package com.lawyaar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.InternalTokenResult
import com.lawyaar.auth.PhoneActivity
import com.lawyaar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setSupportActionBar(binding.appBarMain.toolbar)

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

    private fun init()
    {

        auth = FirebaseAuth.getInstance()
        //auth.set
        auth.getAccessToken(true)
        auth.addIdTokenListener { it: InternalTokenResult ->
            Log.d("TAG", "addIdTokenListener: called--> "+it.token ?: "notoken")

        }

    }
    fun initforAuth()
    {
        FirebaseAuth.getInstance().addIdTokenListener { it: InternalTokenResult ->
            Log.d("TAG", "addIdTokenListener: called--> "+it.token ?: "notoken")

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
       filter_close_icon.setOnClickListener {
           dialog.dismiss()
       }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }
}