package com.lawyaar.application

import android.app.Application
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.retrofit.ApplicationComponent
import com.lawyaar.retrofit.DaggerApplicationComponent

class LawyaarApplication : Application() {

    lateinit var  applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)
          applicationComponent = DaggerApplicationComponent.builder().build()

    }

}