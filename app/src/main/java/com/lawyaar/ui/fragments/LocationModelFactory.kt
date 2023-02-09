package com.lawyaar.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class LocationModelFactory @Inject constructor(private  val repostry: MainRepostry): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationModel(repostry ) as T
    }

}