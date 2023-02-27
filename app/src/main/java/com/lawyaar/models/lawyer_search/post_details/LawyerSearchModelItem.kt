package com.lawyaar.models.lawyer_search.post_details

import android.os.Parcelable



data class LawyerSearchModelItem(
    val advocateDetail: AdvocateDetail,
    val businessTitle: String,
    val caseCategories: List<CaseCategory>,
    val email: String,
    val emailVerified: Boolean,
    val firebaseId: String,
    val languages: List<Language>,
    val mobile: String,
    val name: String,
    val userId: String,
    val username: String
)