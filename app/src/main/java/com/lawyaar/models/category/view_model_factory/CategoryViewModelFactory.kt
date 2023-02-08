package com.lawyaar.models.category.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.language.view_model_factory.LanguageViewModel
import com.lawyaar.retrofit.MainRepostry

class CategoryViewModelFactory  constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(repostry) as T
    }

}