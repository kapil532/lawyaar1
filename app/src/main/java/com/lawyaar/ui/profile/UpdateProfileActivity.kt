package com.lawyaar.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
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
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.LocationModelItem
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.models.location.view_factory_model.LocationViewModelFactory
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsFactoryModel
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsViewModel
import com.lawyaar.ui.base_screen.BaseActivity
import javax.inject.Inject

class UpdateProfileActivity : BaseActivity()
{
    lateinit var caseCategoryViewModel: CaseCategoryViewModel
    lateinit var languageViewModel: LanguageViewModel
    lateinit var locationViewModel: LocationViewModel
    lateinit var userDetailsViewModel: UserDetailsViewModel




    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory
    @Inject
    lateinit var languaViewModelFactory: LanguageViewModelFactory
    @Inject
    lateinit var caseCategoryViewModelFactory: CaseCategoryViewModelFactory
    @Inject
    lateinit var userDetailsFactoryModel: UserDetailsFactoryModel

    lateinit var  update_user_name :EditText
    lateinit var  user_mobileno :EditText
    lateinit var  user_email :EditText
    lateinit var     spinner04 :Spinner

    lateinit var userDetailsModel: UserDetailsModel
    lateinit var  dataSource: ArrayList<CaseCategoryItem>
    lateinit var langModel: ArrayList<LanguageModelItem>
    lateinit var locationModel: ArrayList<LocationModelItem>


    @SuppressLint("MissingIflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_profile_screen)


        val back_icon = findViewById<ImageView>(R.id.back_icon)
        back_icon.setOnClickListener({
            finish()
        })


         update_user_name =  findViewById<EditText>(R.id.update_user_name)
         user_mobileno =  findViewById<EditText>(R.id.user_mobileno)
         user_email =  findViewById<EditText>(R.id.user_email)

        initBottomSheet()
    }


    @SuppressLint("MissingInflatedId")
    fun initBottomSheet()
    {
        val filter_recyle =findViewById<RecyclerView>(R.id.filter_recyle)
        val filter_recyle_langauge =findViewById<RecyclerView>(R.id.filter_recyle_langauge)
        val filter_recyle_location =findViewById<RecyclerView>(R.id.filter_recyle_location)
         spinner04 =findViewById<Spinner>(R.id.spinner04)

        filter_recyle.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        filter_recyle_langauge.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        filter_recyle_location.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


        locationAdaptor = LocationAdaptor()
        languageAdaptor = LanguageAdaptor()
        laywerCategory = LaywerCategoryAdaptor()
        spinnerAdapter = CustomDropDownAdapter(this)




        filter_recyle.adapter=laywerCategory
        spinner04.adapter = spinnerAdapter

        filter_recyle_langauge.adapter=languageAdaptor
        filter_recyle_location.adapter=locationAdaptor


        initNetwork()
    }


    lateinit var locationAdaptor: LocationAdaptor
    lateinit var languageAdaptor: LanguageAdaptor
    lateinit var laywerCategory: LaywerCategoryAdaptor
    lateinit var spinnerAdapter: CustomDropDownAdapter
    val selectedItems_lang = SparseBooleanArray()
    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)

        userDetailsViewModel =ViewModelProvider(this,userDetailsFactoryModel).get(UserDetailsViewModel::class.java)
        locationViewModel = ViewModelProvider(this,locationViewModelFactory).get(LocationViewModel::class.java)
        languageViewModel = ViewModelProvider(this,languaViewModelFactory).get(LanguageViewModel::class.java)
        caseCategoryViewModel = ViewModelProvider(this,caseCategoryViewModelFactory).get(
            CaseCategoryViewModel::class.java)


        locationViewModel.location.observe(this, Observer<LocationModel> {
            if (it != null)
            {
                locationAdaptor.setUpdateData(it)
               locationModel = it
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })

        languageViewModel.language.observe(this, Observer<LanguageModel> {
            if (it != null)
            {
                languageAdaptor.setUpdateData(it)
                langModel = it
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })

        caseCategoryViewModel.category.observe(this, Observer<CaseCategory> {
            if (it != null)
            {
                spinnerAdapter.setUpdateData(it)
                dataSource =it
                getDetails()
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })



    }

    fun getDetails()
    {
        val sharedPreferences: SharedPreferences =application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        val tokenValue = sharedPreferences.getString("token_val", " ")
        val user_id = sharedPreferences.getString("user_id", " ")

        if (tokenValue != null && user_id != null) {
            userDetailsViewModel.getUserDetails(tokenValue,"language,category,locations",user_id)
        }
        userDetailsViewModel.getUserLiveData.observe(this, Observer<UserDetailsModel> {
            if (it != null)
            {
                Log.d("","--> NUL VALUE"+it.name)
                updateDetails(it)
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun updateDetails(userDetailsModel: UserDetailsModel)
    {
        this.userDetailsModel =userDetailsModel
        user_mobileno.setText( userDetailsModel.mobile)
        user_email.setText(userDetailsModel.email)
        update_user_name.setText(userDetailsModel.name)

        getIndexForCase()
        getIndexForLangauge()
    }


    fun getIndexForCase()
    {
        for(dataVal in dataSource)
        {
            val idexVal =dataVal.name.indexOf(userDetailsModel.caseCategories.get(0).name)
             if (idexVal != -1)
             {
                 Log.d("--> VALUES index ",""+idexVal)
                spinner04.setSelection(idexVal)
             }
        }
    }

   var  indexCount = 0
    fun getIndexForLangauge()
    {
            for (lang in userDetailsModel.languages)
            {
                indexCount =0
                for (dataVal in langModel) {
                    val idexVal = dataVal.name.indexOf(lang.name)
                    if (idexVal != -1)
                    {
                        selectedItems_lang.put(indexCount ,true)
                        languageAdaptor.setUpdateSelectiionData(selectedItems_lang,indexCount)
                    }
                    indexCount ++
                }
           }

    }




}