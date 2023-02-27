package com.lawyaar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lawyaar.adapters.*
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.databinding.ActivityMainBinding
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
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.utils.FilterOption

import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.hide();
        binding.appBarMain.fab.setColorFilter(Color.WHITE);
        binding.appBarMain.fab.setOnClickListener { view ->
            initBottomSheet()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



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
        val filter_close_icon = view.findViewById<ImageView>(R.id.filter_close_icon)
        val filter_recyle = view.findViewById<RecyclerView>(R.id.filter_recyle)
        val filter_button = view.findViewById<AppCompatButton>(R.id.filter_button)
        filter_button.setOnClickListener({
            dialog.dismiss()
            getDetails()
        })
        val filter_recyle_langauge = view.findViewById<RecyclerView>(R.id.filter_recyle_langauge)
        val filter_recyle_location = view.findViewById<RecyclerView>(R.id.filter_recyle_location)
         spinner04 = view.findViewById<Spinner>(R.id.spinner04)
         spinner05 = view.findViewById<Spinner>(R.id.spinner05)
       val clear_all_tag = view.findViewById<TextView>(R.id.clear_all_tag)
        clear_all_tag.setOnClickListener({
//            ModelPreferencesManager.deleteAll("FILTER_DETAILS")
//            updateFilterDetails()
            clearFilter()
            dialog.dismiss()
        })

        filter_recyle.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        filter_recyle_langauge.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        filter_recyle_location.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


        locationAdaptor = LocationAdaptor()
        languageAdaptor = LanguageAdaptor()
        laywerCategory = LaywerCategoryAdaptor()
        spinnerAdapter = CustomDropDownAdapter(this)
        spinnerAdapterLocation= LocationDropDownAdapter(this)


        filter_close_icon.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        filter_recyle.adapter = laywerCategory
        spinner04.adapter = spinnerAdapter
        spinner05.adapter = spinnerAdapterLocation
        filter_recyle_langauge.adapter = languageAdaptor
        filter_recyle_location.adapter = locationAdaptor


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
            ViewModelProvider(this, locationViewModelFactory).get(LocationViewModel::class.java)
        languageViewModel =
            ViewModelProvider(this, languaViewModelFactory).get(LanguageViewModel::class.java)
        caseCategoryViewModel = ViewModelProvider(
            this,
            caseCategoryViewModelFactory
        ).get(CaseCategoryViewModel::class.java)
        updateFilterDetails()

        locationViewModel.location.observe(this, Observer<LocationModel> {
            if (it != null)
            {
                spinnerAdapterLocation.setUpdateData(it)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

        languageViewModel.language.observe(this, Observer<LanguageModel> {
            if (it != null)
            {
                languageAdaptor.setUpdateData(it)
                langModel = it
                getIndexForLangauge(languages)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

        caseCategoryViewModel.category.observe(this, Observer<CaseCategory> {
            if (it != null)
            {
                spinnerAdapter.setUpdateData(it)
                dataSource =it
                getIndexForCase(caseCategories)

            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

    }
    fun updateFilterDetails()
    {
        val postDataFilter = ModelPreferencesManager.get<PostDataFilter>("FILTER_DETAILS")
        if(postDataFilter != null)
        {
            caseCategories = postDataFilter.caseCategories
            locations = postDataFilter.locations
            languages = postDataFilter.languages


        }
    }

    lateinit var dataSource: ArrayList<CaseCategoryItem>
    var caseCategories: ArrayList<String> = ArrayList()
    var locations: ArrayList<String> = ArrayList()
    val lawyerCategories: ArrayList<String> = ArrayList()
    val offerPriceRange: ArrayList<Int> = ArrayList()
    val actualPriceRange: ArrayList<Int> = ArrayList()
    var languages: ArrayList<String> = ArrayList()
    fun getDetails()
    {
        caseCategories.clear()
        locations.clear()
        languages.clear()
        languages = languageAdaptor.getAllData()
//        Log.d("DATA","--- >  "+dataSource.get(spinner04.selectedItemPosition).caseId)
        caseCategories.add("" + dataSource.get(spinner04.selectedItemPosition).caseId)
        offerPriceRange.add(550)
        offerPriceRange.add(1650)
        actualPriceRange.add(650)
        actualPriceRange.add(1500)
        if (filterOption != null) {

            var postFilter = PostDataFilter(actualPriceRange,caseCategories,languages,lawyerCategories,locations,offerPriceRange)
//           Log.d("MAINACTIVITY"," values -- > "+postFilter.caseCategories.get(0))
            ModelPreferencesManager.put(postFilter,"FILTER_DETAILS")
            filterOption.updateLawyaarDetails(postFilter)
        }
    }





 fun clearFilter()
 {
     caseCategories.clear()
     locations.clear()
     languages.clear()
     lawyerCategories.clear()
     offerPriceRange.clear()
     actualPriceRange.clear()
     if (filterOption != null) {

         var postFilter = PostDataFilter(actualPriceRange,caseCategories,languages,lawyerCategories,locations,offerPriceRange)
//           Log.d("MAINACTIVITY"," values -- > "+postFilter.caseCategories.get(0))
         ModelPreferencesManager.put(postFilter,"FILTER_DETAILS")
         filterOption.updateLawyaarDetails(postFilter)
     }
 }

    var indexCase = 0
    fun getIndexForCase(caseCategories :ArrayList<String>) {
        indexCase = 0
        for (dataVal in dataSource)
        {
           // Log.d("DATA VALUE","DATA -- > "+dataVal.caseId +"--"+caseCategories.get(0))
            try {
                val idexVal = dataVal.caseId.indexOf(caseCategories.get(0))
//            Log.d("DATA VALUE","DATA -- > "+idexVal)
                if (idexVal != -1) {
                    spinner04.setSelection(indexCase)
                }
            }
            catch (e : java.lang.Exception){

            }

            indexCase++
        }
    }

    val selectedItems_lang = SparseBooleanArray()
    lateinit var langModel: ArrayList<LanguageModelItem>
    var indexCount = 0
    fun getIndexForLangauge(languages: ArrayList<String>)
    {
        for (lang in languages) {
            indexCount = 0
            for (dataVal in langModel) {
//                Log.d("DATA VALUE QQ","DATA -- > "+dataVal.name)
                val idexVal = dataVal.languageId.indexOf(lang)
//                Log.d("DATA VALU QQ","DATA -- > "+idexVal)
                if (idexVal != -1) {
                    selectedItems_lang.put(indexCount, true)
                    languageAdaptor.setUpdateSelectiionData(selectedItems_lang, indexCount)
                }
                indexCount++
            }
        }

    }
}