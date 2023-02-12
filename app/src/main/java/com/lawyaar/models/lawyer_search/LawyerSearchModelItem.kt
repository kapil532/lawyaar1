package com.lawyaar.models.lawyer_search

data class LawyerSearchModelItem(
    val email: String,
    val emailVerified: Boolean,
    val languages: List<Language>,
    val mobile: String,
    val name: String,
    val userId: String,
    val username: String
)