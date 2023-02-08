package com.lawyaar.models.language.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class LanguageViewModelFactory @Inject constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LanguageViewModel(repostry) as T
    }

}