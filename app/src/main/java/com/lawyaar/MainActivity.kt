package com.lawyaar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lawyaar.adapters.LanguageAdaptor
import com.lawyaar.adapters.LaywerCategoryAdaptor
import com.lawyaar.adapters.LocationAdaptor
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.databinding.ActivityMainBinding
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModel
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModelFactory
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.language.view_model_factory.LanguageViewModel
import com.lawyaar.models.language.view_model_factory.LanguageViewModelFactory
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.models.location.view_factory_model.LocationViewModelFactory

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





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
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
    lateinit var token_ : String


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    fun initBottomSheet()
    {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.filter_screen_layout, null)
        val  filter_close_icon = view.findViewById<ImageView>(R.id.filter_close_icon)
        val filter_recyle =view.findViewById<RecyclerView>(R.id.filter_recyle)
        val filter_recyle_langauge =view.findViewById<RecyclerView>(R.id.filter_recyle_langauge)
        val filter_recyle_location =view.findViewById<RecyclerView>(R.id.filter_recyle_location)

        filter_recyle.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        filter_recyle_langauge.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        filter_recyle_location.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


        locationAdaptor = LocationAdaptor()
        languageAdaptor = LanguageAdaptor()
        laywerCategory = LaywerCategoryAdaptor()

        filter_close_icon.setOnClickListener {
           dialog.dismiss()
       }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        filter_recyle.adapter=laywerCategory
        filter_recyle_langauge.adapter=languageAdaptor
        filter_recyle_location.adapter=locationAdaptor


        initNetwork()
    }

    lateinit var locationAdaptor: LocationAdaptor
    lateinit var languageAdaptor: LanguageAdaptor
    lateinit var laywerCategory: LaywerCategoryAdaptor

    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)
        locationViewModel = ViewModelProvider(this,locationViewModelFactory).get(LocationViewModel::class.java)
        languageViewModel = ViewModelProvider(this,languaViewModelFactory).get(LanguageViewModel::class.java)
        caseCategoryViewModel = ViewModelProvider(this,caseCategoryViewModelFactory).get(CaseCategoryViewModel::class.java)


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
                laywerCategory.setUpdateData(it)
            }
          else{
              Log.d("","--> NUL VALUE")
          }
      })


    }
}