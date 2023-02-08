package com.lawyaar.models.location.view_factory_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.location.LocationModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repostry.getLocation()
        }

    }

    val location: LiveData<LocationModel>
        get() = repostry.location

}