package com.lawyaar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.lawyaar.ui.intro_screen.IntroScreen

class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        init()

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        checkvalidity()
//        Handler(Looper.getMainLooper()).postDelayed({
//            checkvalidity()
//        }, 3000)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun checkvalidity() {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("token_auth", Context.MODE_PRIVATE)

        val tokenValue = sharedPreferences.getString("token_val", " ")
        Log.d("TOKEN VALUE", "token--> " + tokenValue)
        if (auth.currentUser != null) {
            if (tokenValue != null && tokenValue.length > 10) {
                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()

            } else {
                val intent = Intent(this, IntroScreen::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this, IntroScreen::class.java)
            startActivity(intent)
            finish()
        }


    }


}