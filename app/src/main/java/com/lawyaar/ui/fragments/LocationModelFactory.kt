package com.lawyaar.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.ui.home.HomeViewModel

class LocationModelFactory(private  val repostry: MainRepostry): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationModel(repostry) as T
    }

}