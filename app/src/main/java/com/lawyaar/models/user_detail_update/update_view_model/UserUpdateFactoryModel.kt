package com.lawyaar.models.user_detail_update.update_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class UserUpdateFactoryModel  @Inject constructor(private val repostry: MainRepostry) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserUpdateViewmodel(repostry) as T
    }
}