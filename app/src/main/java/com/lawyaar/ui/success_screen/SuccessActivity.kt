package com.lawyaar.ui.success_screen

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.lawyaar.R
import com.lawyaar.ui.base_screen.BaseActivity

class SuccessActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_screen_layout)
        val animation_view = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.animation_view)
        animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.e("Animation:", "start")
            }
            override fun onAnimationEnd(animation: Animator) {
                Log.e("Animation:", "end")
              finish()
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.e("Animation:", "repeat")
                finish()
            }

        })
    }


}