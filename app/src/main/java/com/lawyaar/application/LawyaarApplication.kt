package com.lawyaar.application

import android.app.Application
import com.lawyaar.retrofit.ApplicationComponent
import com.lawyaar.retrofit.DaggerApplicationComponent

class LawyaarApplication : Application() {

    lateinit var  applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
          applicationComponent = DaggerApplicationComponent.builder().build()

    }

}