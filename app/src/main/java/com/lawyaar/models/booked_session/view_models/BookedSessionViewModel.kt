package com.lawyaar.models.booked_session.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.booked_session.BookedSessionModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookedSessionViewModel  constructor(private val repostry: MainRepostry) :
    ViewModel() {


    fun getBookedSession(token: String, children :String,userID: String){
        viewModelScope.launch(Dispatchers.IO) {
            repostry.getBookedSession(token,children,userID )
        }
    }
    val bookedSessionLD: LiveData<BookedSessionModel>
        get() = repostry.bookedSessionLD
}