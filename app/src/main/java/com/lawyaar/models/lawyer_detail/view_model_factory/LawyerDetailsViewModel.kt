package com.lawyaar.models.lawyer_detail.view_model_factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.lawyer_detail.LawyerModel
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LawyerDetailsViewModel  constructor(private val repostry: MainRepostry) :
    ViewModel() {



    fun getLawyerDetails(token: String, children: String,userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repostry.getLawyerDetails(token, children, userId)
        }
    }

    val getLawyerLiveData: LiveData<LawyerModel>
        get() = repostry.getLawyerDetailLD
}