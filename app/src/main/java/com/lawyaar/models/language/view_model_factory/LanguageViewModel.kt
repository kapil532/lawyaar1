package com.lawyaar.models.language.view_model_factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.location.LocationModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LanguageViewModel  constructor(private val repostry: MainRepostry) :
    ViewModel() {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repostry.getLanguage()
        }

    }

    val language: LiveData<LanguageModel>
        get() = repostry.language

}