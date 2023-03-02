package com.lawyaar.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lawyaar.ui.auth.OTPActivity
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
import com.lawyaar.models.user_detail_update.UserUpdateModel
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.models.wallets.WalletModel
import com.lawyaar.models.wallets.wallet_post_pojo.WalletPostBody
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


    private val layersBySearchLiveData = MutableLiveData<LawyerSearchModel>()
    val lawyerSearch: LiveData<LawyerSearchModel>
        get() = layersBySearchLiveData

    suspend fun getLawyersSearch(token: String, children: String, postFilter: PostDataFilter) {
        val result = lawyaarApi.getLawyersBySearch(token, children, postFilter)
        if (result?.body() != null) {
            layersBySearchLiveData.postValue(result.body())
        }
    }


    private val userWalletLiveData = MutableLiveData<WalletModel>()
    val walletLive: LiveData<WalletModel>
        get() = userWalletLiveData

    suspend fun getWalletsDetails_R(token: String,userId: String,walletPostBody: WalletPostBody) {
        val result = lawyaarApi.getWalletsDetails(token,userId,walletPostBody)
        if (result?.body() != null) {
            userWalletLiveData.postValue(result.body())
        }
    }



    //Get User details
    private val userDetailsLiveData = MutableLiveData<UserDetailsModel>()
    val userLiveData: LiveData<UserDetailsModel>
        get() = userDetailsLiveData

    suspend fun getUserDetails(token: String, children: String,userId: String) {
        val result = lawyaarApi.getUserDetails(token,children,userId)
        if (result?.body() != null) {
            userDetailsLiveData.postValue(result.body())
        }
    }


    //Update User details
    private val userDetailsUpdateLiveData = MutableLiveData<UserDetailsModel>()
    val userUpdateLiveData: LiveData<UserDetailsModel>
        get() = userDetailsUpdateLiveData

    suspend fun getUserUpdateDetails(token: String, children: String,userId: String, data: UserUpdateModel) {
        val result = lawyaarApi.updateUserDetails(token,children,userId,data )
        if (result?.body() != null) {
            userDetailsUpdateLiveData.postValue(result.body())
        }
    }

  //get Lawyer details
    private val getLawyerDetailM = MutableLiveData<LawyerModel>()
    val getLawyerDetailLD: LiveData<LawyerModel>
        get() = getLawyerDetailM

    suspend fun getLawyerDetails(token: String, children: String,userId: String) {
        val result = lawyaarApi.getLawyerDetails(token,children,userId )
        if (result?.body() != null) {
            getLawyerDetailM.postValue(result.body())
        }
    }


    //get Session Availability details
    private val getSessionAbailablityM = MutableLiveData<SessionAvailability>()
    val getSessionAbailablityL: LiveData<SessionAvailability>
        get() = getSessionAbailablityM

    suspend fun getSessionAbailablity(token: String, userId: String,date :String) {
        val result = lawyaarApi.getSession(token,userId,date )
        if (result?.body() != null) {
            getSessionAbailablityM.postValue(result.body())
        }
    }


    //Book Session Availability details
    private val bookSessionMLD = MutableLiveData<String>()
    val bookSessionLD: LiveData<String>
        get() = bookSessionMLD

    suspend fun bookSession(token: String, clientId: String,userID :String,  data: BookSessionPojo) {
        val result = lawyaarApi.bookSession(token,clientId,userID,data )
        if (result?.body() != null) {
            bookSessionMLD.postValue(result.body())
        }
    }

 //Booked Session Availability details
    private val bookedSessionMLD = MutableLiveData<BookedSessionModel>()
    val bookedSessionLD: LiveData<BookedSessionModel>
        get() = bookedSessionMLD

    suspend fun getBookedSession(token: String, children :String,userID: String,date :String,  ) {
        val result = lawyaarApi.getBookedSessions(token,children,userID,date )
        if (result?.body() != null) {
            bookedSessionMLD.postValue(result.body())
        }
    }


}