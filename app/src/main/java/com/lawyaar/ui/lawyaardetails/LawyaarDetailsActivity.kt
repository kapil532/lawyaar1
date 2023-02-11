package com.lawyaar.ui.lawyaardetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lawyaar.R
import com.lawyaar.databinding.ActivityMainBinding

class LawyaarDetailsActivity : AppCompatActivity()
{

  //lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  binding = ActivityMainBinding.inflate(layoutInflater)
       // val view = binding.root
        setContentView(R.layout.lawyer_profile_layout)

         // to bazsck the past activity
        val back_icon = findViewById<ImageView>(R.id.back_icon)
                 back_icon.setOnClickListener({
                      finish()
                 })


    }




}