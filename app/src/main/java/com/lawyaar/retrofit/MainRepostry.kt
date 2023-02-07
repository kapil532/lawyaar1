package com.lawyaar.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.testlist.QuoteList
import javax.inject.Inject

class MainRepostry @Inject constructor(private val lawyaarApi: LawyaarApi) {


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
        }

    }

}