package com.lawyaar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.language.LanguageModelItem
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.LocationModelItem
import com.lawyaar.ui.book_slots.adaptors.BookingDateAdaptar

class LanguageAdaptor (): RecyclerView.Adapter<LanguageAdaptor.ViewHolder>()
{
    class ViewHolder(view :View) :RecyclerView.ViewHolder(view)
    {
           val single_loc_lan_title :TextView

           init {
               single_loc_lan_title   = view.findViewById(R.id.date_slots_text)
           }


    }

    var list = ArrayList<LanguageModelItem>()

    fun setUpdateData(quoteList: ArrayList<LanguageModelItem>)
    {
        this.list =  quoteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.single_row_time_booking_slot,parent,false)
        return LanguageAdaptor.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.single_loc_lan_title.text = list.get(position).name
    }


}