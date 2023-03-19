package com.lawyaar.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lawyaar.R
import com.lawyaar.adapters.CustomDropDownAdapter
import com.lawyaar.adapters.LanguageAdaptor
import com.lawyaar.adapters.LaywerCategoryAdaptor
import com.lawyaar.adapters.LocationAdaptor
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.case_category.CaseCategoryItem
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModel
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModelFactory
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.language.LanguageModelItem
import com.lawyaar.models.language.view_model_factory.LanguageViewModel
import com.lawyaar.models.language.view_model_factory.LanguageViewModelFactory
import com.lawyaar.models.lawyer_search.post_details.PostFilter
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.LocationModelItem
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.models.location.view_factory_model.LocationViewModelFactory
import com.lawyaar.models.user_detail_update.UserUpdateModel
import com.lawyaar.models.user_detail_update.update_view_model.UserUpdateFactoryModel
import com.lawyaar.models.user_detail_update.update_view_model.UserUpdateViewmodel
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsFactoryModel
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsViewModel
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.ui.success_screen.SuccessActivity
import javax.inject.Inject

class UpdateProfileActivity : BaseActivity() {


    lateinit var caseCategoryViewModel: CaseCategoryViewModel
    lateinit var languageViewModel: LanguageViewModel
    lateinit var locationViewModel: LocationViewModel
    lateinit var userDetailsViewModel: UserDetailsViewModel

    lateinit var userUpdateViewmodel: UserUpdateViewmodel


    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory
    @Inject
    lateinit var languaViewModelFactory: LanguageViewModelFactory
    @Inject
    lateinit var caseCategoryViewModelFactory: CaseCategoryViewModelFactory
    @Inject
    lateinit var userDetailsFactoryModel: UserDetailsFactoryModel
    @Inject
    lateinit var userUpdateFactoryModel: UserUpdateFactoryModel

    lateinit var update_user_name: EditText
    lateinit var update_profile_b: Button
    lateinit var user_mobileno: EditText
    lateinit var user_email: EditText
    lateinit var spinner04: Spinner

    lateinit var userDetailsModel: UserDetailsModel
    lateinit var dataSource: ArrayList<CaseCategoryItem>
    lateinit var langModel: ArrayList<LanguageModelItem>
    lateinit var locationModel: ArrayList<LocationModelItem>


    @SuppressLint("MissingIflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_profile_screen)


        val back_icon = findViewById<ImageView>(R.id.back_icon)
      back_icon.setOnClickListener { finish() }


        update_user_name = findViewById<EditText>(R.id.update_user_name)
        user_mobileno = findViewById<EditText>(R.id.user_mobileno)
        user_email = findViewById<EditText>(R.id.user_email)


        update_profile_b = findViewById<Button>(R.id.update_profile_b)
        update_profile_b.setOnClickListener(
            {
                updateUserDetails()
            }
        )
        initBottomSheet()
    }


    @SuppressLint("MissingInflatedId")
    fun initBottomSheet() {
        val filter_recyle = findViewById<RecyclerView>(R.id.filter_recyle)
        val filter_recyle_langauge = findViewById<RecyclerView>(R.id.filter_recyle_langauge)
        val filter_recyle_location = findViewById<RecyclerView>(R.id.filter_recyle_location)
        spinner04 = findViewById<Spinner>(R.id.spinner04)

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




        filter_recyle.adapter = laywerCategory
        spinner04.adapter = spinnerAdapter

        filter_recyle_langauge.adapter = languageAdaptor
        filter_recyle_location.adapter = locationAdaptor


        initNetwork()
    }


    lateinit var locationAdaptor: LocationAdaptor
    lateinit var languageAdaptor: LanguageAdaptor
    lateinit var laywerCategory: LaywerCategoryAdaptor
    lateinit var spinnerAdapter: CustomDropDownAdapter
    val selectedItems_lang = SparseBooleanArray()
    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)

        userDetailsViewModel =
            ViewModelProvider(this, userDetailsFactoryModel).get(UserDetailsViewModel::class.java)
        locationViewModel =
            ViewModelProvider(this, locationViewModelFactory).get(LocationViewModel::class.java)
        languageViewModel =
            ViewModelProvider(this, languaViewModelFactory).get(LanguageViewModel::class.java)
        caseCategoryViewModel = ViewModelProvider(this, caseCategoryViewModelFactory).get(
            CaseCategoryViewModel::class.java
        )


        locationViewModel.location.observe(this, Observer<LocationModel> {
            if (it != null) {
                locationAdaptor.setUpdateData(it)
                locationModel = it
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

        languageViewModel.language.observe(this, Observer<LanguageModel> {
            if (it != null) {
                languageAdaptor.setUpdateData(it)
                langModel = it
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

        caseCategoryViewModel.category.observe(this, Observer<CaseCategory> {
            if (it != null) {
                spinnerAdapter.setUpdateData(it)
                dataSource = it
                getDetails()
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })


    }

    var user_id = ""
    var tokenValue = ""
    fun getDetails() {
        val sharedPreferences: SharedPreferences =
            application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        user_id = sharedPreferences.getString("user_id", " ").toString()

        if (tokenValue != null && user_id != null) {

            userDetailsViewModel.getUserDetails(tokenValue, "language,category,locations", user_id)

        }
        userDetailsViewModel.getUserLiveData.observe(this, Observer<UserDetailsModel> {
            if (it != null) {
                updateDetails(it)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun updateDetails(userDetailsModel: UserDetailsModel) {
        this.userDetailsModel = userDetailsModel
        user_mobileno.setText(userDetailsModel.mobile)
        user_email.setText(userDetailsModel.email)
        update_user_name.setText(userDetailsModel.name)

        getIndexForCase()
        getIndexForLangauge()
    }


    var indexCase = 0
    fun getIndexForCase() {
        indexCase = 0
        for (dataVal in dataSource) {
            try {

                val idexVal = dataVal.name.indexOf(userDetailsModel.caseCategories.get(0).name)
                if (idexVal != -1) {
                    spinner04.setSelection(indexCase)
                }
            }
            catch (e : java.lang.Exception)
            {

            }
            indexCase++
        }
    }

    var indexCount = 0
    fun getIndexForLangauge() {
        try {


            for (lang in userDetailsModel.languages) {
                indexCount = 0
                for (dataVal in langModel) {
                    val idexVal = dataVal.name.indexOf(lang.name)
                    if (idexVal != -1) {
                        selectedItems_lang.put(indexCount, true)
                        languageAdaptor.setUpdateSelectiionData(selectedItems_lang, indexCount)
                    }
                    indexCount++
                }
            }
        }
        catch (e : java.lang.Exception)
        {

        }
    }

    var caseCategories: ArrayList<String> = ArrayList()

    fun updateUserDetails() {

        val email: String
        val languages: ArrayList<String>
        val name: String
        val userId: String


        email = "" + user_email.text
        name = "" + update_user_name.text
        userId = user_id
        languages = languageAdaptor.getAllData()
        caseCategories.add("" + dataSource.get(spinner04.selectedItemPosition).caseId)
        var UserUpdateModel = UserUpdateModel(caseCategories, email, languages, name, userId)

        userUpdateViewmodel =
            ViewModelProvider(this, userUpdateFactoryModel).get(UserUpdateViewmodel::class.java)
        userUpdateViewmodel.getUserUpdateDetails(
            tokenValue,
            "role,language,category,locations",
            userId,
            UserUpdateModel
        )
        userUpdateViewmodel.getUserUpdateLiveData.observe(this, Observer<UserDetailsModel> {
            if (it != null) {

              //  updateDetails(it)
                val intent = Intent(this, SuccessActivity::class.java)
                // intent.putExtra("userId" , lawyerDetails)
                startActivity(intent)
                finish()
            } else {
            }
        })

    }


}