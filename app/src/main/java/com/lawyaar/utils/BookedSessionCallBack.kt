package com.lawyaar.utils

import com.lawyaar.models.booked_session.BookedSessionModelItem

interface BookedSessionCallBack
{
    fun onCellClickListener(bookedSessionModelItem : BookedSessionModelItem)
}