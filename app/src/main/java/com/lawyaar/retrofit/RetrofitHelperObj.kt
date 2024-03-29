package com.lawyaar.retrofit

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



object RetrofitHelperObj {

    val baseUrl = "https://quotable.io/"



    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun provideQuotaionAPI(retrofit: Retrofit) :LawyaarApi
    {
        return retrofit.create(LawyaarApi::class.java)
    }
}