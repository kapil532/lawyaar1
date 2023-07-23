package com.lawyaar.models.booked_session


import com.google.gson.annotations.SerializedName

data class AppointmentBookedModelItem(
    @SerializedName("advocateEmail")
    val advocateEmail: String?,
    @SerializedName("advocateId")
    val advocateId: String?,
    @SerializedName("advocateMobile")
    val advocateMobile: String?,
    @SerializedName("advocateName")
    val advocateName: String?,
    @SerializedName("clientEmail")
    val clientEmail: String?,
    @SerializedName("clientId")
    val clientId: String?,
    @SerializedName("clientMobile")
    val clientMobile: String?,
    @SerializedName("clientName")
    val clientName: String?,
    @SerializedName("sessionDate")
    val sessionDate: String?,
    @SerializedName("sessionId")
    val sessionId: String?,
    @SerializedName("sessionStatus")
    val sessionStatus: String?,
    @SerializedName("sessionTime")
    val sessionTime: String?
)