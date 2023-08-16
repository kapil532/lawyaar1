package com.lawyaar.models.lawyer_search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LawyerSearchViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {
        fun lawyerSearchByFilter(token: String, children: String, postFilter: PostDataFilter) {
            viewModelScope.launch(Dispatchers.IO) {
                repostry.getLawyersSearch(token, children, postFilter)
            }
        }

    val searchLawyerLiveData: LiveData<LawyerSearchModel>
        get() = repostry.lawyerSearch

}