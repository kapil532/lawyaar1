package com.lawyaar.models.lawyer_search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class LawyerSearchFactoyModel @Inject constructor(private val repostry: MainRepostry) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LawyerSearchViewModel(repostry) as T
    }
} 