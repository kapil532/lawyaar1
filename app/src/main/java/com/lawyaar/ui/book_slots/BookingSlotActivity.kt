package com.lawyaar.ui.book_slots

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.book_slots.adaptors.BookingDateAdaptar
import com.lawyaar.ui.book_slots.adaptors.BookingTimeAdaper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class BookingSlotActivity : BaseActivity() {

    val arr = arrayOf("AM", "PM")
    lateinit var recyclerViewtime: RecyclerView
    lateinit var recyclerViewDate: RecyclerView
    lateinit var calenderView: CalendarView


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_slot_activity)

        recyclerViewDate = findViewById<RecyclerView>(R.id.date_slots)
        recyclerViewtime = findViewById<RecyclerView>(R.id.time_slots)
        calenderView = findViewById<CalendarView>(R.id.calender_view_for_date)
        val back_icon_book_slot = findViewById<ImageView>(R.id.back_icon_book_slot)
        back_icon_book_slot.setOnClickListener({
            finish()
        })

        initDateForm()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initDateForm() {
        val c = Calendar.getInstance()

        val timmm = c.get(Calendar.AM_PM)
        val timmma = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
        Log.d("-->", "Date " + minute + "   " + timmm + "  " + timmma)

        val arrtime = arrayOf(
            "4:30 PM",
            "4:45 PM",
            "5:00 PM",
            "5:30 PM",
            "6:00 PM",
            "6:30 PM",
            "4:30 PM",
            "4:45 PM"
        )
        val layoutManager = GridLayoutManager(this, 3)
        recyclerViewtime.layoutManager = layoutManager
//        recyclerViewtime.setLayoutManager(layoutManager);
        getDateTomorrow()
        val adapter = BookingTimeAdaper(arrtime)
        recyclerViewtime.adapter = adapter

        setDateUtill()


    }


    fun setDateUtill() {
        val now = System.currentTimeMillis() - 1000

        calenderView.minDate = now
        calenderView.maxDate = now + 1000 * 60 * 60 * 24 * 7
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTomorrow() {
        val tomorrow = LocalDate.now().plus(1, ChronoUnit.DAYS)
        val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("d MMM EE"))

        val today = LocalDate.now().plus(0, ChronoUnit.DAYS)
        val formattedToday = today.format(DateTimeFormatter.ofPattern("d MMM EE"))
        val arrDate = arrayOf(formattedToday, formattedTomorrow)

        recyclerViewDate.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        val adapter = BookingDateAdaptar(arrDate)
        recyclerViewDate.adapter = adapter
    }


}