package com.lawyaar.ui.book_slots

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.LanguageAdaptor
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.book_session.BookSessionPojo
import com.lawyaar.models.book_session.view_model.BookingSessionFactoryModel
import com.lawyaar.models.book_session.view_model.BookingSessionViewModel
import com.lawyaar.models.session.SessionAvailability
import com.lawyaar.models.session.session_view_model.SessionFactoryModel
import com.lawyaar.models.session.session_view_model.SessionViewModel
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.book_slots.adaptors.BookingTimeAdaper
import com.lawyaar.ui.success_screen.SuccessActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject


class BookingSlotActivity : BaseActivity() {


    val arr = arrayOf("AM", "PM")
    lateinit var recyclerViewtime: RecyclerView
    lateinit var recyclerViewDate: RecyclerView
    lateinit var appoint_button: Button
    lateinit var no_slots: TextView
    lateinit var calenderView: CalendarView

    lateinit var sessionViewModel: SessionViewModel

    @Inject
    lateinit var sessionFactoryModel: SessionFactoryModel


    lateinit var curDate: String
    lateinit var curYear: String
    lateinit var curMonth: String
    lateinit var finalString: String
    private lateinit var uSSERID: String
    private lateinit var userID: String

    private lateinit var adapter: BookingTimeAdaper

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_slot_activity)
        uSSERID = intent.getStringExtra("userId").toString()
        recyclerViewDate = findViewById<RecyclerView>(R.id.date_slots)
        appoint_button = findViewById<Button>(R.id.appoint_button)
        appoint_button.setOnClickListener({
            bookingSlots()
        })
        recyclerViewtime = findViewById<RecyclerView>(R.id.time_slots)
        no_slots = findViewById<TextView>(R.id.no_slots)
        no_slots.visibility = View.GONE
        calenderView = findViewById<CalendarView>(R.id.calender_view_for_date)
        val back_icon_book_slot = findViewById<ImageView>(R.id.back_icon)
        back_icon_book_slot.setOnClickListener({
            finish()
        })
        calenderView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            curDate = dayOfMonth.toString()
            curMonth = (month + 1).toString()
            curYear = year.toString()
            finalString = curDate + "-" + curMonth + "-" + curYear
            //            "26-02-2023"
            Log.d("CURSAT", "--> " + finalString)
            getDateWiseSlots(uSSERID, finalString)

        })
        initNetwork()
        initDateForm()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initDateForm() {
        val c = Calendar.getInstance()

        val d = c.get(Calendar.DAY_OF_MONTH)
        val m = c.get(Calendar.MONTH) + 1
        val y = c.get(Calendar.YEAR)

        finalString = "" + d + "-" + m + "-" + y
        //            "26-02-2023"
        Log.d("CURSAT", "--> " + finalString)
        getDateWiseSlots(uSSERID, finalString)
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
        //getDateTomorrow()
        adapter = BookingTimeAdaper()
        recyclerViewtime.adapter = adapter

        setDateUtill()


    }


    fun setDateUtill() {
        val now = System.currentTimeMillis() - 1000

        calenderView.minDate = now
        calenderView.maxDate = now + 1000 * 60 * 60 * 24 * 7
    }

    lateinit var languageAdaptor: LanguageAdaptor

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTomorrow() {
        val tomorrow = LocalDate.now().plus(1, ChronoUnit.DAYS)
        val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("d MMM EE"))

        val today = LocalDate.now().plus(0, ChronoUnit.DAYS)
        val formattedToday = today.format(DateTimeFormatter.ofPattern("d MMM EE"))
        val arrDate = arrayOf(formattedToday, formattedTomorrow)

        recyclerViewDate.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        // adapter = BookingDateAdaptar(arrDate)
      //  recyclerViewDate.adapter = adapter

        initNetwork()

    }

    var tokenValue = ""
    fun initNetwork() {
        val sharedPreferences: SharedPreferences =
            application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        userID = sharedPreferences.getString("user_id", " ").toString()
        (application as LawyaarApplication).applicationComponent.inject(this)

    }

   lateinit var  selectedDate :String


    fun getDateWiseSlots(advocateId: String, date: String) {

        selectedDate =date
        sessionViewModel =
            ViewModelProvider(this, sessionFactoryModel).get(SessionViewModel::class.java)
        Log.d("USER ID-- >", " USERID AND DATE" + advocateId + "   " + date)
        sessionViewModel.getSessionAbailablity(tokenValue, advocateId, date)
        sessionViewModel.getSessionAbailablityL.observe(
            this,
            androidx.lifecycle.Observer<SessionAvailability> {
                if (it != null) {
                    Log.d("ITTTT", "-->  " + it)
                    if (it.size > 0) {
                        recyclerViewtime.visibility = View.VISIBLE
                        adapter.setUpdateData(it)
                        no_slots.visibility = View.GONE
                        appoint_button.visibility = View.VISIBLE
                    } else {
                        Log.d("ITTTT", "-->  NULL")
                        recyclerViewtime.visibility = View.GONE
                        no_slots.visibility = View.VISIBLE
                        appoint_button.visibility = View.GONE
                    }

                } else {
                    Log.d("ITTTT", "-->  NULL")
                    no_slots.visibility = View.VISIBLE
                    appoint_button.visibility = View.GONE
                }


            })
    }


    lateinit var bookingSessionViewModel: BookingSessionViewModel

    @Inject
    lateinit var bookSessionFactoryModel: BookingSessionFactoryModel



    fun bookingSlots() {
        val timeSel = adapter.getSelectedTime()

        Log.d("BOOKINGSLOTS", selectedDate+  "  TIMESET-->" + timeSel+"  --  "+userID+"  "+uSSERID  +"  "+tokenValue)
        val bookSessionPojo = BookSessionPojo(uSSERID,selectedDate,timeSel,userID)

        bookingSessionViewModel = ViewModelProvider(this, bookSessionFactoryModel).get(BookingSessionViewModel::class.java)
        bookingSessionViewModel.bookSession(tokenValue,userID,uSSERID,bookSessionPojo)
        bookingSessionViewModel.bookSessionLD.observe(this, androidx.lifecycle.Observer<String>{
          Log.d("VALUE","BOOKING DONE "+it)
            if(it.equals("true"))
            {
                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this,"Please try again!",Toast.LENGTH_LONG).show()
            }
        })

    }
}