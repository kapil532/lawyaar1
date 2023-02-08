package com.lawyaar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.lawyaar.auth.PhoneActivity

class SplashScreen : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        init()
        Handler(Looper.getMainLooper()).postDelayed({
           checkvalidity()
        }, 3000)
    }

    private fun init()
    {

        auth = FirebaseAuth.getInstance()
    }

    private fun checkvalidity()
    {
//        if (auth.currentUser != null){
//            startActivity(Intent(this , MainActivity::class.java))
//            finish()
//        }
//        else{
            val intent = Intent(this, PhoneActivity::class.java)
            startActivity(intent)
            finish()
//       }


    }



}