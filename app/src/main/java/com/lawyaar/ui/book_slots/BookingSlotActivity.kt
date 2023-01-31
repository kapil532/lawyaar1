package com.lawyaar.ui.book_slots

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.R
import java.util.Calendar

class BookingSlotActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_slot_activity)
        initDateForm()
    }

    fun initDateForm()
    {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
   Log.d("-->","Date " + year +","+month+"  "+day+" "+ hour+" "+minute )
    }


}