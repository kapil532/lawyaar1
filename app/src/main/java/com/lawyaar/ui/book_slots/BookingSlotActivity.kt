package com.lawyaar.ui.book_slots

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.HorizontalScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.R
import com.lawyaar.ui.book_slots.adaptors.BookingDateAdaptar
import com.lawyaar.ui.book_slots.adaptors.BookingTimeAdaper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import kotlin.math.log

class BookingSlotActivity : BaseActivity()
{

     val arr = arrayOf("AM" ,"PM")
    lateinit var  recyclerViewtime : RecyclerView
    lateinit var  recyclerViewDate : RecyclerView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_slot_activity)
         recyclerViewDate = findViewById<RecyclerView>(R.id.date_slots)
         recyclerViewtime = findViewById<RecyclerView>(R.id.time_slots)
        initDateForm()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initDateForm()
    {
        val c = Calendar.getInstance()

        val timmm= c.get(Calendar.AM_PM)
        val timmma= c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
       Log.d("-->","Date " +minute+"   "+ timmm + "  "+timmma)

        val arrtime=  arrayOf("4:30" ,"4:45" ,"5:00","5:30","6:00" ,"6:30","4:30" ,"4:45" ,"5:00","5:30","6:00" ,"6:30")
        val layoutManager = GridLayoutManager(this, 5)
        recyclerViewtime.layoutManager =  layoutManager
//        recyclerViewtime.setLayoutManager(layoutManager);
        getDateTomorrow()
        val adapter = BookingTimeAdaper(arrtime)
        recyclerViewtime.adapter = adapter


    }

     @RequiresApi(Build.VERSION_CODES.O)
     fun getDateTomorrow(){
         val tomorrow = LocalDate.now().plus(1, ChronoUnit.DAYS)
         val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("d MMM EE"))

         val today = LocalDate.now().plus(0, ChronoUnit.DAYS)
         val formattedToday = today.format(DateTimeFormatter.ofPattern("d MMM EE"))
          val arrDate = arrayOf(formattedToday,formattedTomorrow)

         recyclerViewDate.layoutManager =LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
         val adapter = BookingDateAdaptar(arrDate)
         recyclerViewDate.adapter = adapter
    }


}