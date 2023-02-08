package com.lawyaar.models.authentication.auth_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.testlist.QuoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthModel constructor(private val repostry: MainRepostry) :
    ViewModel() {


    fun authUser(token :String ,mobileNo : String ) {
        viewModelScope.launch(Dispatchers.IO) {
            repostry.getAuthSuccess(token, mobileNo)
        }
    }

    val authSuccess: LiveData<AuthSuccess>
        get() = repostry.authData

}