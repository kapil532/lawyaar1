package com.lawyaar.models.location.view_factory_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.retrofit.MainRepostry

class LocationViewModelFactory constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel(repostry) as T
    }

}