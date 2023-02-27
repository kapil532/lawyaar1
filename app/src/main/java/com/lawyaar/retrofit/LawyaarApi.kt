package com.lawyaar.retrofit

import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.category.CategoryModel
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.lawyer_detail.LawyerModel
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModel
import com.lawyaar.models.lawyer_search.post_details.PostFilter
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.user_detail_update.UserUpdateModel
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.models.wallets.WalletModel
import com.lawyaar.models.wallets.wallet_post_pojo.WalletPostBody
import com.lawyaar.testlist.QuoteList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LawyaarApi {

    @GET("/quotes?page=1")
    suspend fun getQuotes(): Response<QuoteList>


    @GET("firebase/validate/{mobileno}")
    suspend fun getAuthFirebase(
        @Header("Authorization") token: String,
        @Path("mobileno") mobileno: String
    ): Response<AuthSuccess>


    @GET("common/location/all")
    suspend fun getLocation(): Response<LocationModel>

    @GET("common/language/all")
    suspend fun getLanguage(): Response<LanguageModel>

    @GET("common/lawyerCategory/all")
    suspend fun getLaweyCategory(): Response<CategoryModel>

    @GET("common/caseCategory/all")
    suspend fun getCaseCategory(): Response<CaseCategory>


    @GET("users/{userId}")
    suspend fun getUserDetails(
        @Header("Authorization") token: String,
        @Header("children") children: String,
        @Path("userId") userId: String
    ): Response<UserDetailsModel>


    @POST("users/search")
    suspend fun getLawyersBySearch(
        @Header("Authorization") token: String,
        @Header("children") children: String,
        @Body data: PostDataFilter
    ): Response<LawyerSearchModel>




    @POST("users/user/{userId}/update")
    suspend fun updateUserDetails(
        @Header("Authorization") token: String,
        @Header("children") children: String,
        @Path("userId") userId: String,
        @Body data: UserUpdateModel
    ): Response<UserDetailsModel>


    @GET("advocates/{userId}")
    suspend fun getLawyerDetails(
        @Header("Authorization") token: String,
        @Header("children") children: String,
        @Path("userId") userId: String,
    ): Response<LawyerModel>

    //add points pending
    @POST("wallet/{userId}/points/")
    suspend fun addPoints (
        @Header("Authorization") token: String,
        @Header("children") children: String,
        @Path("userId") userId: String,
        @Body data: UserUpdateModel
    ): Response<UserDetailsModel>

    //wallet

    @POST("wallet/{userId}/list")
    suspend fun getWalletsDetails(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
         @Body data : WalletPostBody):
            Response<WalletModel>

}