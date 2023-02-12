package com.lawyaar.ui.success_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.lawyaar.R
import com.lawyaar.ui.base_screen.BaseActivity

class SuccessActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_screen_layout)
        Handler(Looper.getMainLooper()).postDelayed({
            checkvalidity()
        }, 3000)
    }

    fun checkvalidity()
    {
        finish()
    }
}