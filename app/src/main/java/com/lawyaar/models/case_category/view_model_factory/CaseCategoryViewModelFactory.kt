package com.lawyaar.models.case_category.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.category.view_model_factory.CategoryViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class CaseCategoryViewModelFactory @Inject constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CaseCategoryViewModel(repostry) as T
    }

}