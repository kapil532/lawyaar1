package com.lawyaar.models.lawyer_search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModel
import com.lawyaar.models.lawyer_search.post_details.PostFilter
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LawyerSearchViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {



        fun lawyerSearchByFilter(token: String, children: String, postFilter: PostFilter) {
            viewModelScope.launch(Dispatchers.IO) {
                repostry.getLawyersSearch(token, children, postFilter)
            }
        }

    val searchLawyerLiveData: LiveData<LawyerSearchModel>
        get() = repostry.lawyerSearch

}