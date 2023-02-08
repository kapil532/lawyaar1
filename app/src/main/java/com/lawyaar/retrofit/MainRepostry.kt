package com.lawyaar.retrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lawyaar.MainActivity
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.auth.OTPActivity
import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.testlist.QuoteList
import dagger.hilt.android.internal.Contexts.getApplication
import javax.inject.Inject


class MainRepostry  @Inject constructor(private val lawyaarApi: LawyaarApi) {


    private val quotationLiveData = MutableLiveData<QuoteList>()
    val quotes: LiveData<QuoteList>
        get() = quotationLiveData

    suspend fun getAllquotes() {
        val result = lawyaarApi.getQuotes()
        if (result?.body() != null) {
            quotationLiveData.postValue(result.body())

        }
    }


    private val authLiveData = MutableLiveData<AuthSuccess>()
    val authData: LiveData<AuthSuccess>
        get() = authLiveData

    suspend fun getAuthSuccess( token : String , mobileNo  : String) {
        val result = lawyaarApi.getAuthFirebase(token,mobileNo)
        if (result?.body() != null) {
            authLiveData.postValue(result.body())
            OTPActivity.authToken = ""+result.headers().get("Authorization")
            Log.d("Exception","Value Null "+result.headers().get("Authorization"))
        }
        else{
            Log.d("Exception","Value Null "+result.headers())
            Log.d("Exception","Value Null "+result.raw())
        }

    }

}