package com.lawyaar.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.retrofit.MainRepostry

class HomeViewModelFactory(private  val repostry: MainRepostry):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repostry) as T
    }



}