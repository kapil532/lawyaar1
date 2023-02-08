package com.lawyaar.retrofit

import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.category.CategoryModel
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.location.LocationModel
import com.lawyaar.testlist.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface LawyaarApi {

    @GET("/quotes?page=1")
    suspend fun getQuotes(): Response<QuoteList>


    @GET("firebase/validate/{mobileno}")
    suspend fun getAuthFirebase(
        @Header("Authorization") token: String,
        @Path("mobileno") mobileno: String
    ): Response<AuthSuccess>


    @GET("/common/location/all")
    suspend fun getLocation(): Response<LocationModel>

    @GET("/common/language/all")
    suspend fun getLanguage(): Response<LanguageModel>

    @GET("/common/lawyerCategory/all")
    suspend fun getLaweyCategory(): Response<CategoryModel>


    @GET("/common/caseCategory/all")
    suspend fun getCaseCategory(): Response<CaseCategory>


}