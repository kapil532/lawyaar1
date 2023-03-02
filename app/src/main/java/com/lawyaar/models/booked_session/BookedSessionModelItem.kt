package com.lawyaar.models.booked_session

data class BookedSessionModelItem(
    val actualPrice: Int,
    val advocateDetailId: String,
    val advocateId: String,
    val caseCategories: List<CaseCategory>,
    val email: String,
    val experience: String,
    val languages: List<Language>,
    val location: Location,
    val mobile: String,
    val name: String,
    val offerPrice: Int,
    val sessionDate: String,
    val sessionId: String,
    val sessionTime: String
)