package com.lawyaar.models.booked_session


import com.google.gson.annotations.SerializedName

data class AppointmentBookedRequest(
    @SerializedName("advocateId")
    val advocateId: String?,
    @SerializedName("clientId")
    val clientId: String?,
    @SerializedName("sessionStatusList")
    val sessionStatusList: List<String?>?
)