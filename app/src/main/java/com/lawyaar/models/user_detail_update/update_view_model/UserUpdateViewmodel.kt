package com.lawyaar.models.user_detail_update.update_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.user_detail_update.UserUpdateModel
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserUpdateViewmodel constructor(private val mainRepostry: MainRepostry) :ViewModel()
{
    fun getUserUpdateDetails(token: String, children: String,userId: String, data: UserUpdateModel) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepostry.getUserUpdateDetails(token, children, userId,data)
        }
    }

    val getUserUpdateLiveData: LiveData<UserDetailsModel>
        get() = mainRepostry.userUpdateLiveData

}