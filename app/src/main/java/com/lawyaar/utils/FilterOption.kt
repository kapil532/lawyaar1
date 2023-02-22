package com.lawyaar.utils

import com.lawyaar.models.lawyer_search.post_data.PostDataFilter

interface FilterOption
{
    fun updateLawyaarDetails(postDataFilter: PostDataFilter)
}