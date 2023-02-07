package com.lawyaar.models.authentication

data class BadRequest(
    val errorCode: Int,
    val errorMessage: String,
    val errorStatus: String,
    val timestamp: Long
)