package com.lawyaar.retrofit

import com.lawyaar.testlist.QuoteList
import retrofit2.Response
import retrofit2.http.GET

interface LawyaarApi
{

    @GET("/quotes")
    suspend fun getQuotes() : Response<QuoteList>


}