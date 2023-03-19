package com.lawyaar.models.lawyaar_details_transfer

import com.lawyaar.models.lawyer_search.post_details.CaseCategory
import com.lawyaar.models.lawyer_search.post_details.Language


data class LawyaarTransfer(
    val advocateDetailId: String,
    val advocateId: String,
    val caseCategories: List<CaseCategory>,
    val languages: List<Language>,
    val name: String,
    )
