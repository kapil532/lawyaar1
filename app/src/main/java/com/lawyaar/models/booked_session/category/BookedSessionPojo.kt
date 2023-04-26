package com.lawyaar.models.booked_session.category

data class BookedSessionPojo(
    val advocateId: String,
    val clientId: String,
    val sessionStatusList: ArrayList<String>
)