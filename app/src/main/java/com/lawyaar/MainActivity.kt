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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.InternalTokenResult
import com.lawyaar.adapters.LocationAdaptor
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.databinding.ActivityMainBinding
import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.models.authentication.auth_model.AuthModel
import com.lawyaar.models.authentication.auth_model.AuthModelFactory
import com.lawyaar.retrofit.LawyaarApi
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.fragments.LocationModel
import com.lawyaar.ui.fragments.LocationModelFactory
import com.lawyaar.ui.home.HomeViewModel
import com.lawyaar.ui.home.HomeViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setColorFilter(Color.WHITE);
        binding.appBarMain.fab.setOnClickListener { view ->

            initBottomSheet()
//            Snackbar.make(view, "search", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//            if (auth.currentUser != null){
//                auth.signOut()
//                startActivity(Intent(this , PhoneActivity::class.java))
//                finish()
//            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    lateinit var token_ : String
    private fun init()
    {

        auth = FirebaseAuth.getInstance()
        //auth.set
        auth.getAccessToken(true)
        auth.addIdTokenListener { it: InternalTokenResult ->
            Log.d("TAG", "addIdTokenListener: called--> "+it.token ?: "notoken")
            token_ =it.token ?: "notoken"
            initNetworkAA()
        }

    }


    fun initforAuth()
    {
        FirebaseAuth.getInstance().addIdTokenListener { it: InternalTokenResult ->
            Log.d("TAG", "addIdTokenListener: called--> "+it.token ?: "notoken")
            token_ =it.token ?: "notoken"
        }
    }
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

//        filter_recyle.layoutManager = GridLayoutManager(this, 5)
        filter_recyle.layoutManager= StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)
        locationAdaptor = LocationAdaptor()
        filter_close_icon.setOnClickListener {
           dialog.dismiss()
       }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
        filter_recyle.adapter=locationAdaptor
        initNetwork()
    }
    lateinit var locationModel: LocationModel
    lateinit var locationAdaptor: LocationAdaptor

    @SuppressLint("FragmentLiveDataObserve")
    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)
        homeViewModel = ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)

        homeViewModel.quotes.observe(this, Observer<QuoteList> {
            if (it != null)
            {
                locationAdaptor.setUpdateData(it.results)
            }
        })

    }
    @Inject
    lateinit var authModelFactory : AuthModelFactory
    lateinit var authModel: AuthModel



    fun initNetworkAA()
    {

        (application as LawyaarApplication).applicationComponent.inject(this)
        authModel = ViewModelProvider(this ,authModelFactory ).get(AuthModel::class.java)
        authModel.authUser("Bearer "+token_ ,"+918095128426")
        authModel.authSuccess.observe(this, Observer<AuthSuccess> {
            if (it != null)
            {

                Log.d("TAGSS","-fffffff->  "+it.userId +"---   ");

                // locationAdaptor.setUpdateData(it.results)
            }
        })
    }



}