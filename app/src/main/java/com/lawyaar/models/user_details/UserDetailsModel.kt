package com.lawyaar.models.user_details

data class UserDetailsModel(
    val email: String,
    val emailVerified: Boolean,
    val languages: List<Language>,
    val mobile: String,
    val name: String,
    val roles: String,
    val userId: String,
    val username: String
)