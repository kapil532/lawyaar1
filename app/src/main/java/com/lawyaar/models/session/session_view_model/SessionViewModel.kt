package com.lawyaar.models.session.session_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.lawyer_detail.LawyerModel
import com.lawyaar.models.session.SessionAvailability
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionViewModel constructor(private val repostry: MainRepostry) :
    ViewModel()
{

    fun getSessionAbailablity(token: String, userId: String,date :String)
    {
        viewModelScope.launch(Dispatchers.IO) {
           repostry.getSessionAbailablity(token,userId,date)
        }
    }

    val getSessionAbailablityL: LiveData<SessionAvailability>
        get() = repostry.getSessionAbailablityL


}