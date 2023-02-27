package com.lawyaar.ui.book_slots

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.session.SessionAvailability
import com.lawyaar.models.session.session_view_model.SessionFactoryModel
import com.lawyaar.models.session.session_view_model.SessionViewModel
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.book_slots.adaptors.BookingDateAdaptar
import com.lawyaar.ui.book_slots.adaptors.BookingTimeAdaper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject


class BookingSlotActivity : BaseActivity() {


    val arr = arrayOf("AM", "PM")
    lateinit var recyclerViewtime: RecyclerView
    lateinit var recyclerViewDate: RecyclerView
    lateinit var calenderView: CalendarView

    lateinit var sessionViewModel: SessionViewModel

    @Inject
    lateinit var sessionFactoryModel: SessionFactoryModel
    lateinit var curDate: String
    lateinit var curYear: String
    lateinit var curMonth: String
    lateinit var finalString: String
    private lateinit var uSSERID: String


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_slot_activity)
        uSSERID = intent.getStringExtra("userId").toString()
        recyclerViewDate = findViewById<RecyclerView>(R.id.date_slots)
        recyclerViewtime = findViewById<RecyclerView>(R.id.time_slots)
        calenderView = findViewById<CalendarView>(R.id.calender_view_for_date)
        val back_icon_book_slot = findViewById<ImageView>(R.id.back_icon_book_slot)
        back_icon_book_slot.setOnClickListener({
            finish()
        })
        calenderView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            curDate = dayOfMonth.toString()
            curMonth = month.toString()
            curYear = year.toString()
            finalString = curDate+"-"+curMonth+"-"+curYear
            //            "26-02-2023"
            Log.d("CURSAT","--> "+finalString)
            getDateWiseSlots(uSSERID,finalString)

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

        initNetwork()

    }

    var tokenValue =""
    fun initNetwork()
    {
        val sharedPreferences: SharedPreferences =
           application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        (application as LawyaarApplication).applicationComponent.inject(this)

    }

    fun getDateWiseSlots( advocateId : String ,date : String)
    {
        sessionViewModel =
            ViewModelProvider(this, sessionFactoryModel).get(SessionViewModel::class.java)
        sessionViewModel.getSessionAbailablity(tokenValue, advocateId,date)
        sessionViewModel.getSessionAbailablityL.observe(this,androidx.lifecycle.Observer<SessionAvailability>{
            if(it !=null)
            {
             Log.d("ITTTT","-->  "+it)
            }
            else
            {
                Log.d("ITTTT","-->  NULL")
            }


        })
    }

}