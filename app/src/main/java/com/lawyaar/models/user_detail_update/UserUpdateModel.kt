package com.lawyaar.models.user_detail_update

data class UserUpdateModel(
    val caseCategories: List<String>,
    val email: String,
    val languages: List<String>,
    val name: String,
    val userId: String
)