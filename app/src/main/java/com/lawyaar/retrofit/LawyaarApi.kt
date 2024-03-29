package com.lawyaar.retrofit

import com.lawyaar.models.authentication.AuthSuccess
import com.lawyaar.models.book_session.BookSessionPojo
import com.lawyaar.models.booked_session.BookedSessionModel
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.category.CategoryModel
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.lawyer_detail.LawyerModel
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModel
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.session.SessionAvailability
import com.lawyaar.models.token_update.TokenBody
import com.lawyaar.models.token_update.token_response.TokenResponse
import com.lawyaar.models.user_detail_update.UserUpdateModel
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.models.wallet_details.AddWalletModel
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


    @POST("users/search?page=1&size=20")
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
        @Path("userId") userId: String
    ): Response<LawyerModel>

    //add points pending


    @POST("wallet/{userId}/points/{points}")
    suspend fun addPoints(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("points") points: String
    ): Response<AddWalletModel>

    //    wallet/{{userId}}/list
    //wallet
    @POST("wallet/{userId}/list")
    suspend fun listPoints(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Response<String>

    @POST("wallet/{userId}/list")
    suspend fun getWalletsDetails(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body data: WalletPostBody
    ):
            Response<WalletModel>

//    session/{{userId}}/availability/26-02-2023


    @GET("session/{userId}/availability/{date}")
    suspend fun getSession(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("date") date: String
    ): Response<SessionAvailability>

//    session/{{clientId}}/availability/{{userId}}

    @POST("session/{clientId}/availability/{userId}")
    suspend fun bookSession(
        @Header("Authorization") token: String,
        @Path("clientId") clientId: String,
        @Path("userId") userId: String,
        @Body data: BookSessionPojo
    ): Response<String>


    @GET("session/{userId}/booking")
    suspend fun getBookedSessions(
        @Header("Authorization") token: String,
        @Header("children") children: String,
        @Path("userId") userId: String,

        ): Response<BookedSessionModel>

    @POST("firebase/token/{userId}")
    suspend fun postToken(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body data: TokenBody
        ): Response<TokenResponse>



}