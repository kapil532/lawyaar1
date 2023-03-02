package com.lawyaar.models.booked_session.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.case_category.view_model_factory.CaseCategoryViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class BookedSessionFactoryModel @Inject constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookedSessionViewModel(repostry) as T
    }

}