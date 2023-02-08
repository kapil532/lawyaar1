package com.lawyaar.retrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lawyaar.MainActivity
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.auth.OTPActivity
import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.category.CategoryModel
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.fragments.LocationModel
import dagger.hilt.android.internal.Contexts.getApplication
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

    suspend fun getAuthSuccess(token: String, mobileNo: String) {
        val result = lawyaarApi.getAuthFirebase(token, mobileNo)
        if (result?.body() != null) {
            authLiveData.postValue(result.body())
            OTPActivity.authToken = "" + result.headers().get("Authorization")
            Log.d("Exception", "Value Null " + result.headers().get("Authorization"))
        } else {
            Log.d("Exception", "Value Null " + result.headers())
            Log.d("Exception", "Value Null " + result.raw())
        }

    }


    private val locationLiveData = MutableLiveData<LocationModel>()
    val location: LiveData<LocationModel>
        get() = locationLiveData

    suspend fun getLocation() {
        val result = lawyaarApi.getLocation()
        if (result?.body() != null) {
            locationLiveData.postValue(result.body())
        }
    }


    private val languageLiveData = MutableLiveData<LanguageModel>()
    val language: LiveData<LanguageModel>
        get() = languageLiveData

    suspend fun getLanguage() {
        val result = lawyaarApi.getLanguage()
        if (result?.body() != null) {
            languageLiveData.postValue(result.body())
        }
    }


    private val laweyCategoryLiveData = MutableLiveData<CategoryModel>()
    val laweyCategory: LiveData<CategoryModel>
        get() = laweyCategoryLiveData

    suspend fun getLaweyCategory() {
        val result = lawyaarApi.getLaweyCategory()
        if (result?.body() != null) {
            laweyCategoryLiveData.postValue(result.body())
        }
    }


    private val caseCategoryLiveData = MutableLiveData<CaseCategory>()
    val caseCategory: LiveData<CaseCategory>
        get() = caseCategoryLiveData

    suspend fun getCaseCategory() {
        val result = lawyaarApi.getCaseCategory()
        if (result?.body() != null) {
            caseCategoryLiveData.postValue(result.body())
        }
    }

}