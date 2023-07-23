package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.booked_session.AppointmentBookedModelItem
import com.lawyaar.utils.BookedSessionCallBack
import com.lawyaar.utils.getDateFormatted

class CancelledAppointmentAdapter : RecyclerView.Adapter<CancelledAppointmentAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cancelLawyerName: TextView = itemView.findViewById(R.id.cancel_lawyer_name)
        val bookedLawyerExpert: TextView = itemView.findViewById(R.id.cancel_lawyer_expert)
        val bookedLawyerProfile: ImageView = itemView.findViewById(R.id.cancel_lawyer_profile)
        val cancelSessionDate: TextView = itemView.findViewById(R.id.cancel_session_date)
    }

    var list = ArrayList<AppointmentBookedModelItem>()

    fun setUpdateData(list: ArrayList<AppointmentBookedModelItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    private lateinit var cellClickListener: BookedSessionCallBack

    fun setUplistner(cellClickListener: BookedSessionCallBack) {
        this.cellClickListener = cellClickListener

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cancel_appointment_items, viewGroup, false)
        return MyViewHolder(v)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val currentItem = list[i]
        viewHolder.cancelLawyerName.text = currentItem.advocateName
        //viewHolder.bookedLawyerExpert.text = currentItem.sessionTime
        //val date = getDateFormatted(currentItem.sessionDate)
        viewHolder.cancelSessionDate.text = getDateFormatted(currentItem.sessionDate)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}