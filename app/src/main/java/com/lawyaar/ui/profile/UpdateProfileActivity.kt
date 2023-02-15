package com.lawyaar.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.lawyaar.R
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
    }





}