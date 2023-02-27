package com.lawyaar.models.session

data class SessionAvailabilityItem(
    val advocateDetailId: String,
    val sessionDate: String,
    val sessionTime: String,
    val slotAvailability: Int
)