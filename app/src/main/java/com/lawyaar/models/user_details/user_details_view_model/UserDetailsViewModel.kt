package com.lawyaar.models.user_details.user_details_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel constructor(private val mainRepostry: MainRepostry) :ViewModel()
{

    fun getUserDetails(token: String, children: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepostry.getUserDetails(token, children, userId)
        }
    }
    val getUserLiveData: LiveData<UserDetailsModel>
        get() = mainRepostry.userLiveData
}