package com.lawyaar.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.contracts.Returns

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    val baseUrl = "http://13.233.146.29/api/v1/"


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideQuotaionAPI(retrofit: Retrofit): LawyaarApi {
        return retrofit.create(LawyaarApi::class.java)
    }


}