package com.lawyaar.models.lawyer_search.post_data

data class PostDataFilter(
    val actualPriceRange: List<Int>,
    val caseCategories: List<String>,
    val languages: List<String>,
    val lawyerCategories: List<String>,
    val locations: List<String>,
    val offerPriceRange: List<Int>
)