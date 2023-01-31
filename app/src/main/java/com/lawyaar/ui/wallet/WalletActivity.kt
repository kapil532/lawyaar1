package com.lawyaar.ui.wallet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lawyaar.R
import com.lawyaar.payment_screen.PaymentActivity

class WalletActivity : AppCompatActivity()
{

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_slideshow)
        val payment_button = findViewById<Button>(R.id.payment_button)
        payment_button.setOnClickListener({

            startActivity(Intent(this@WalletActivity , PaymentActivity::class.java))
        })


    }


}