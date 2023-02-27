package com.lawyaar.models.session.session_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.user_detail_update.update_view_model.UserUpdateViewmodel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class SessionFactoryModel @Inject constructor(private val repostry: MainRepostry) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SessionViewModel(repostry) as T
    }
}