package com.lawyaar.models.book_session.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.book_session.BookSessionPojo
import com.lawyaar.models.session.SessionAvailability
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingSessionViewModel  constructor(private val repostry: MainRepostry) :
    ViewModel()
{


        fun bookSession(token: String, clientId: String,userID :String,  data: BookSessionPojo)
        {
            viewModelScope.launch(Dispatchers.IO) {
                repostry.bookSession(token,clientId,userID,data )
            }
        }

    val bookSessionLD: LiveData<String>
        get() = repostry.bookSessionLD
}