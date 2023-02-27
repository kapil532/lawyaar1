package com.lawyaar.ui.book_slots.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.models.session.SessionAvailabilityItem

class BookingTimeAdaper() : RecyclerView.Adapter<BookingTimeAdaper.ViewHolder>()
{
      class ViewHolder(veiw : View) :RecyclerView.ViewHolder(veiw)
      {
          val date_slots_text: TextView
          val slots_available_down_date: TextView

          init {
              // Define click listener for the ViewHolder's View
              date_slots_text = veiw.findViewById(R.id.date_slots_text)
              slots_available_down_date = veiw.findViewById(R.id.slots_available_down_date)

          }
      }


    var dataSet =ArrayList<SessionAvailabilityItem>()

    fun setUpdateData( dataSet: ArrayList<SessionAvailabilityItem>)
    {
        this.dataSet =  dataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val view =  LayoutInflater.from(parent.context).inflate(R.layout.single_row_time_booking_slot,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {

             holder.date_slots_text.text = dataSet[position].sessionTime
//             holder.slots_available_down_date.text = dataSet[position]
    }

}