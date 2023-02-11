package com.lawyaar.ui.lawyaardetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lawyaar.R
import com.lawyaar.databinding.ActivityMainBinding

class LawyaarDetailsActivity : AppCompatActivity()
{

  //lateinit var binding: ActivityMainBinding
    @SuppressLint("SuspiciousIndentation")
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

      val appoint_button = findViewById<Button>(R.id.appoint_button)
                 back_icon.setOnClickListener({
                      finish()
                 })


      initializeView()

    }
    fun initializeView()
    {
        val rating_no =findViewById<TextView>(R.id.rating_no)
        val lawyaar_name =findViewById<TextView>(R.id.lawyaar_name)
        val lawyaar_exper =findViewById<TextView>(R.id.lawyaar_exper)
        val experience =findViewById<TextView>(R.id.experience)
        val lawyer_reveiw =findViewById<TextView>(R.id.lawyer_reveiw)
        val about_lawyer =findViewById<TextView>(R.id.about_lawyer)
        val cases_count =findViewById<TextView>(R.id.cases_count)
        val price_hour =findViewById<TextView>(R.id.price_hour)
        val reveiw_count =findViewById<TextView>(R.id.reveiw_count)
    }




}