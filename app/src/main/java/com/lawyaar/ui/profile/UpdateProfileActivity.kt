package com.lawyaar.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModel
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.language.view_model_factory.LanguageViewModel
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.ui.base_screen.BaseActivity

class UpdateProfileActivity : BaseActivity()
{


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_profile_screen)


        val back_icon = findViewById<ImageView>(R.id.back_icon)
        back_icon.setOnClickListener({
            finish()
        })


        val update_user_name =  findViewById<EditText>(R.id.update_user_name)
        val user_mobileno =  findViewById<EditText>(R.id.user_mobileno)
        val user_email =  findViewById<EditText>(R.id.user_email)

        initBottomSheet()
    }


    @SuppressLint("MissingInflatedId")
    fun initBottomSheet()
    {
        val  filter_close_icon = findViewById<ImageView>(R.id.filter_close_icon)
        val filter_recyle =findViewById<RecyclerView>(R.id.filter_recyle)
        val filter_recyle_langauge =findViewById<RecyclerView>(R.id.filter_recyle_langauge)
        val filter_recyle_location =findViewById<RecyclerView>(R.id.filter_recyle_location)
        val spinner04 =findViewById<Spinner>(R.id.spinner04)

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

    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)
        locationViewModel = ViewModelProvider(this,locationViewModelFactory).get(LocationViewModel::class.java)
        languageViewModel = ViewModelProvider(this,languaViewModelFactory).get(LanguageViewModel::class.java)
        caseCategoryViewModel = ViewModelProvider(this,caseCategoryViewModelFactory).get(
            CaseCategoryViewModel::class.java)


        locationViewModel.location.observe(this, Observer<LocationModel> {
            if (it != null)
            {
                locationAdaptor.setUpdateData(it)
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })

        languageViewModel.language.observe(this, Observer<LanguageModel> {
            if (it != null)
            {
                languageAdaptor.setUpdateData(it)
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })

        caseCategoryViewModel.category.observe(this, Observer<CaseCategory> {
            if (it != null)
            {
                spinnerAdapter.setUpdateData(it)
            }
            else{
                Log.d("","--> NUL VALUE")
            }
        })


    }


}