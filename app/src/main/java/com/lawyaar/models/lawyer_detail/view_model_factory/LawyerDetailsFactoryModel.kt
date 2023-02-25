package com.lawyaar.models.lawyer_detail.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class LawyerDetailsFactoryModel  @Inject constructor(private val repostry: MainRepostry) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LawyerDetailsViewModel(repostry) as T
    }
}