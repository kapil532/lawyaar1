package com.lawyaar.models.token_update.token_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.session.SessionAvailability
import com.lawyaar.models.token_update.TokenBody
import com.lawyaar.models.token_update.token_response.TokenResponse
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TokenViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {

    fun postToken(token: String, userId: String, body: TokenBody) {
        viewModelScope.launch(Dispatchers.IO) {
            repostry.postToken(token, userId, body)
        }
    }

    val getToken: LiveData<TokenResponse>
        get() = repostry.tokenDataLD

}