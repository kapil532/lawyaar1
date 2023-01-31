package com.lawyaar.ui.book_slots.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R

class BookingDateAdaptar(private val dataSet : Array<String>) : RecyclerView.Adapter<BookingDateAdaptar.ViewHolder>()
{

       class  ViewHolder(view :View) : RecyclerView.ViewHolder(view)
       {
           val date_slots_text: TextView
           val slots_available_down_date: TextView

           init {
               // Define click listener for the ViewHolder's View
               date_slots_text = view.findViewById(R.id.date_slots_text)
               slots_available_down_date = view.findViewById(R.id.slots_available_down_date)

           }
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {

        val view =  LayoutInflater.from(parent.context).inflate(R.layout.single_row_time_slots,parent,false)
        return BookingDateAdaptar.ViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
    }


}