package com.lawyaar.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.testlist.QuoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationModel  constructor(private val repostry: MainRepostry) : ViewModel()
{

//    init {
//        viewModelScope.launch (Dispatchers.IO){
//            repostry.getAllquotes()
//
//        }
//    }
//        val quotes : LiveData<QuoteList>
//        get() = repostry.quotes

}