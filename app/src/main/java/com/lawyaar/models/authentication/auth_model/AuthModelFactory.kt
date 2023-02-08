package com.lawyaar.models.authentication.auth_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.retrofit.MainRepostry
import dagger.Provides
import javax.inject.Inject

class AuthModelFactory  @Inject constructor(private val repostry: MainRepostry ) :ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthModel(repostry) as T
    }
}