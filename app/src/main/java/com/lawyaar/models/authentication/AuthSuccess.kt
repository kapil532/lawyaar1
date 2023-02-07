package com.lawyaar.models.authentication

data class AuthSuccess(
    val emailVerified: Boolean,
    val firebaseId: String,
    val mobile: String,
    val roles: String,
    val userId: String,
    val username: String
)