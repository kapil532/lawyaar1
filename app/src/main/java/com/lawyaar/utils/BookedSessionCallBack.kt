package com.lawyaar.utils

import com.lawyaar.models.booked_session.BookedSessionModelItem
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem

interface BookedSessionCallBack
{

    fun onCellClickListener(bookedSessionModelItem : BookedSessionModelItem)

}