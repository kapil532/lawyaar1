package com.lawyaar.utils

import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem

interface CellClickListener
{
    fun onCellClickListener(lawyerDetails : LawyerSearchModelItem)
}