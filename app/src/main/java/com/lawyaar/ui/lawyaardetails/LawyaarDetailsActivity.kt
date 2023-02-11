package com.lawyaar.ui.lawyaardetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lawyaar.R

class LawyaarDetailsActivity : AppCompatActivity()
{


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             setContentView(R.layout.lawyer_profile_layout)

         // to back the past activity
        val back_icon = findViewById<ImageView>(R.id.back_icon)
                 back_icon.setOnClickListener({
                      finish()
                 })


    }




}