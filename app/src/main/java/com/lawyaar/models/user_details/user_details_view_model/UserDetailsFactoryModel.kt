package com.lawyaar.models.user_details.user_details_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class UserDetailsFactoryModel @Inject constructor(private val repostry: MainRepostry) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(repostry) as T
    }
}