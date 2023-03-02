package com.lawyaar.models.book_session.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.session.session_view_model.SessionViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class BookingSessionFactoryModel  @Inject constructor(private val repostry: MainRepostry) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookingSessionViewModel(repostry) as T
    }
}