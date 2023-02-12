package com.lawyaar.models.lawyer_search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LawyerSearchViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            //repostry.get
        }

    }

    val language: LiveData<LanguageModel>
        get() = repostry.language

}