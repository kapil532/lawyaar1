package com.lawyaar.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lawyaar.testlist.QuoteList

class MainRepostry constructor(private val lawyaarApi: LawyaarApi)
{


     private val quotationLiveData = MutableLiveData<QuoteList>()
     val quotes:LiveData<QuoteList>
     get() = quotationLiveData
    suspend fun getAllquotes(){
        val result = lawyaarApi.getQuotes()
         if(result?.body() != null){
              quotationLiveData.postValue(result.body())

         }
    }


}