package com.lawyaar.models.lawyer_detail

data class LawyerModel(
    val actualPrice: String,
    val advocateDetailId: String,
    val availability: String,
    val description: String,
    val experience: String,
    val lawyerCategories: List<LawyerCategory>,
    val location: Location,
    val offerPrice: String,
    val userId: String,
    val verified: Int
)