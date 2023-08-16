package com.lawyaar

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.SparseBooleanArray
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.BuildConfig
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lawyaar.adapters.*
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.databinding.HomeScreenActivityBinding
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.case_category.CaseCategoryItem
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModel
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModelFactory
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.language.LanguageModelItem
import com.lawyaar.models.language.view_model_factory.LanguageViewModel
import com.lawyaar.models.language.view_model_factory.LanguageViewModelFactory
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.models.location.view_factory_model.LocationViewModelFactory
import com.lawyaar.models.token_update.TokenBody
import com.lawyaar.models.token_update.token_view_model.TokenFactoryModel
import com.lawyaar.models.token_update.token_view_model.TokenViewModel
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.ui.home.HomeFragment
import com.lawyaar.ui.profile.UpdateProfileActivity
import com.lawyaar.ui.slideshow.BookedAppointmentFragment
import com.lawyaar.utils.FilterOption

import javax.inject.Inject

class HomeScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: HomeScreenActivityBinding
    lateinit var caseCategoryViewModel: CaseCategoryViewModel
    lateinit var languageViewModel: LanguageViewModel
    lateinit var locationViewModel: LocationViewModel

    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory

    @Inject
    lateinit var languaViewModelFactory: LanguageViewModelFactory

    @Inject
    lateinit var caseCategoryViewModelFactory: CaseCategoryViewModelFactory

    companion object {
        lateinit var filterOption: FilterOption
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val appointmentFragment = BookedAppointmentFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_home -> {
                    binding.headers.txtHeader.text = getString(R.string.menu_home)
                    binding.edtSearchInclude.searchMain.visibility = View.VISIBLE
                    setCurrentFragment(homeFragment)
                }
                R.id.bottom_nav_appointment -> {
                    binding.headers.txtHeader.text = getString(R.string.header_appointment)
                    binding.edtSearchInclude.searchMain.visibility = View.GONE
                    setCurrentFragment(appointmentFragment)
                }
                R.id.bottom_nav_profile -> {
                    startActivity(
                        Intent(
                            this@HomeScreenActivity,
                            UpdateProfileActivity::class.java
                        )
                    )
                }
            }
            true
        }
        binding.edtSearchInclude.filterIcon.setOnClickListener {
            initBottomSheet()
        }
        localSearchData()
    }

    private fun localSearchData() {
        binding.edtSearchInclude.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(searchChar: CharSequence?, start: Int, before: Int, count: Int) {
                // mArrayAdapter.filter.filter(s)
                filterOption.localSearch(searchChar)
            }

            override fun afterTextChanged(s: Editable?) {
                // Do Nothing
            }

        })
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private lateinit var navController: NavController
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.nav_home) {
            drawerLayout.close()
            navController.navigate(R.id.nav_home)
        }
        if (item.itemId == R.id.share) {
            drawerLayout.close()
            shareApp(this)
        }
        if (item.itemId == R.id.rate_lawyaar) {
            drawerLayout.close()
            openPlayStore()
        }

        if (item.itemId == R.id.nav_gallery) {
            drawerLayout.close()
            navController.navigate(R.id.nav_gallery)
        }
        if (item.itemId == R.id.nav_slideshow) {
            drawerLayout.close()
            navController.navigate(R.id.nav_slideshow)
        }
        if (item.itemId == R.id.nav_profileshow) {
            drawerLayout.close()
            navController.navigate(R.id.nav_profileshow)
        }

        if (item.itemId == R.id.nav_setting) {
            drawerLayout.close()
            navController.navigate(R.id.nav_setting)
        }
        return true
    }

    lateinit var drawerLayout: DrawerLayout
    lateinit var token_: String
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    lateinit var spinner04: Spinner
    lateinit var spinner05: Spinner

    @SuppressLint("MissingInflatedId")
    fun initBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.filter_screen_layout, null)
        val filterCloseIcon = view.findViewById<ImageView>(R.id.filter_close_icon)
        val filterRecycle = view.findViewById<RecyclerView>(R.id.filter_recyle)
        val filterButton = view.findViewById<AppCompatButton>(R.id.filter_button)
        filterButton.setOnClickListener {
            dialog.dismiss()
            getDetails()
        }
        val filterRecycleLanguage = view.findViewById<RecyclerView>(R.id.filter_recyle_langauge)
        val filterRecycleLocation = view.findViewById<RecyclerView>(R.id.filter_recyle_location)
        spinner04 = view.findViewById(R.id.spinner04)
        spinner05 = view.findViewById(R.id.spinner05)
        val clearAllTag = view.findViewById<TextView>(R.id.clear_all_tag)
        clearAllTag.setOnClickListener {
            clearFilter()
            dialog.dismiss()
        }

        filterRecycle.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        filterRecycleLanguage.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        filterRecycleLocation.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


        locationAdaptor = LocationAdaptor()
        languageAdaptor = LanguageAdaptor()
        laywerCategory = LaywerCategoryAdaptor()
        spinnerAdapter = CustomDropDownAdapter(this)
        spinnerAdapterLocation = LocationDropDownAdapter(this)

        filterCloseIcon.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        filterRecycle.adapter = laywerCategory
        spinner04.adapter = spinnerAdapter
        spinner05.adapter = spinnerAdapterLocation
        filterRecycleLanguage.adapter = languageAdaptor
        filterRecycleLanguage.adapter = locationAdaptor
        initNetwork()
    }


    lateinit var locationAdaptor: LocationAdaptor
    lateinit var languageAdaptor: LanguageAdaptor
    lateinit var laywerCategory: LaywerCategoryAdaptor
    lateinit var spinnerAdapter: CustomDropDownAdapter
    lateinit var spinnerAdapterLocation: LocationDropDownAdapter

    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)
        locationViewModel =
            ViewModelProvider(this, locationViewModelFactory)[LocationViewModel::class.java]
        languageViewModel =
            ViewModelProvider(this, languaViewModelFactory)[LanguageViewModel::class.java]
        caseCategoryViewModel = ViewModelProvider(
            this,
            caseCategoryViewModelFactory
        )[CaseCategoryViewModel::class.java]
        //5  getDetails()

        locationViewModel.location.observe(this, Observer<LocationModel> {
            if (it != null) {
                spinnerAdapterLocation.setUpdateData(it)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

        languageViewModel.language.observe(this, Observer<LanguageModel> {
            if (it != null) {
                languageAdaptor.setUpdateData(it)
                langModel = it
                getIndexForLangauge(languages)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

        caseCategoryViewModel.category.observe(this, Observer<CaseCategory> {
            if (it != null) {
                spinnerAdapter.setUpdateData(it)
                dataSource = it
                getIndexForCase(caseCategories)

            } else {
                Log.d("", "--> NUL VALUE")
            }
        })
        val sharedPreference1 = getSharedPreferences("device_token_update", Context.MODE_PRIVATE)
        val tokenBool = sharedPreference1.getString("device_token_update", "00").toString()
        if (tokenBool == "false") {
            sendNotification()
        } else {
            Log.d("", "-->Token false")
        }

    }

    fun updateFilterDetails() {
        val postDataFilter = ModelPreferencesManager.get<PostDataFilter>("FILTER_DETAILS")
        caseCategories = postDataFilter.caseCategories
        locations = postDataFilter.locations
        languages = postDataFilter.languages
    }

    lateinit var dataSource: ArrayList<CaseCategoryItem>
    var caseCategories: ArrayList<String> = ArrayList()
    var locations: ArrayList<String> = ArrayList()
    val lawyerCategories: ArrayList<String> = ArrayList()
    val offerPriceRange: ArrayList<Int> = ArrayList()
    val actualPriceRange: ArrayList<Int> = ArrayList()
    var languages: ArrayList<String> = ArrayList()
    fun getDetails() {
        caseCategories.clear()
        locations.clear()
        languages.clear()
        languages = languageAdaptor.getAllData()
        caseCategories.add("" + dataSource.get(spinner04.selectedItemPosition).caseId)
        offerPriceRange.add(550)
        offerPriceRange.add(1650)
        actualPriceRange.add(650)
        actualPriceRange.add(1500)
        val postFilter = PostDataFilter(
            actualPriceRange,
            caseCategories,
            languages,
            lawyerCategories,
            locations,
            offerPriceRange
        )
        ModelPreferencesManager.put(postFilter, "FILTER_DETAILS")
        filterOption.updateLawyaarDetails(postFilter)
    }

    private fun clearFilter() {
        caseCategories.clear()
        locations.clear()
        languages.clear()
        lawyerCategories.clear()
        offerPriceRange.clear()
        actualPriceRange.clear()
        val postFilter = PostDataFilter(
            actualPriceRange,
            caseCategories,
            languages,
            lawyerCategories,
            locations,
            offerPriceRange
        )
        ModelPreferencesManager.put(postFilter, "FILTER_DETAILS")
        filterOption.updateLawyaarDetails(postFilter)
    }

    var indexCase = 0
    private fun getIndexForCase(caseCategories: ArrayList<String>) {
        for ((indexCase, dataVal) in dataSource.withIndex()) {
            try {
                val idexVal = dataVal.caseId.indexOf(caseCategories.get(0))
                if (idexVal != -1) {
                    spinner04.setSelection(indexCase)
                }
            } catch (e: java.lang.Exception) {
                Log.d("DATA VALUE QQ", "DATA -- > " + dataVal.name)
            }

        }
    }

    val selectedItems_lang = SparseBooleanArray()
    lateinit var langModel: ArrayList<LanguageModelItem>
    var indexCount = 0
    private fun getIndexForLangauge(languages: ArrayList<String>) {
        for (lang in languages) {
            for ((indexCount, dataVal) in langModel.withIndex()) {
                val idexVal = dataVal.languageId.indexOf(lang)
                if (idexVal != -1) {
                    selectedItems_lang.put(indexCount, true)
                    languageAdaptor.setUpdateSelectiionData(selectedItems_lang, indexCount)
                }
            }
        }

    }

    private fun shareApp(context: Context) {
        val appPackageName = BuildConfig.APPLICATION_ID
        val appName = context.getString(R.string.app_name)
        val shareBodyText =
            "Check out the Lawyaar App at: https://play.google.com/store/apps/details?id=$appPackageName"

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, appName)
            putExtra(Intent.EXTRA_TEXT, shareBodyText)
        }
        context.startActivity(Intent.createChooser(sendIntent, null))
    }

    private fun openPlayStore() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    lateinit var tokenViewModel: TokenViewModel

    @Inject
    lateinit var tokenFactoryModel: TokenFactoryModel
    var user_id = ""
    var tokenValue = ""
    var firebase_token = ""
    private fun sendNotification() {
        val sharedPreferences: SharedPreferences =
            application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        user_id = sharedPreferences.getString("user_id", " ").toString()

        val sharedPreference = getSharedPreferences("device_token", Context.MODE_PRIVATE)
        firebase_token = sharedPreference.getString("device_token", "00").toString()
        val tokenBody = TokenBody(firebase_token)
        tokenViewModel = ViewModelProvider(this, tokenFactoryModel).get(TokenViewModel::class.java)
        tokenViewModel.postToken(tokenValue, user_id, tokenBody)
        tokenViewModel.getToken.observe(this, Observer {
            val sharedPref = getSharedPreferences("device_token_update", Context.MODE_PRIVATE)
            sharedPref.edit().putString("device_token_update", "true").apply()

        })
    }
}