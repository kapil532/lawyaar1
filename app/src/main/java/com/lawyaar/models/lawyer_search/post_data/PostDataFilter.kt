package com.lawyaar.models.lawyer_search.post_data

data class PostDataFilter(
    val actualPriceRange: ArrayList<Int>,
    val caseCategories: ArrayList<String>,
    val languages: ArrayList<String>,
    val lawyerCategories: ArrayList<String>,
    val locations: ArrayList<String>,
    val offerPriceRange: ArrayList<Int>
)