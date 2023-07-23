package com.lawyaar.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

const val INPUT_DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd'T'HH:mm:ss.SSS"
const val OUTPUT_DATE_FORMAT_DD_MMM_YY = "dd MMM, yyyy"

@SuppressLint("SimpleDateFormat")
fun getDateFormatted(inputDate: String?): String? {
    try {
        val inputFormat = SimpleDateFormat(INPUT_DATE_FORMAT_YYYY_MM_DD)
        val outputFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT_DD_MMM_YY)
        val date: Date = inputDate?.let { inputFormat.parse(it) } as Date
        return outputFormat.format(date)
    } catch (e: Exception) {
        Log.d("Date format", "Date formatter issue")
    }
    return null
}