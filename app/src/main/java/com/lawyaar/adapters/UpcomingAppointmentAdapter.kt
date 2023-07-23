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

class UpcomingAppointmentAdapter : RecyclerView.Adapter<UpcomingAppointmentAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val upcomingLawyerName: TextView = itemView.findViewById(R.id.upcoming_lawyer_name)
        val upcomingLawyerExpert: TextView = itemView.findViewById(R.id.upcoming_lawyer_expert)
        val upcomingLawyerProfile: ImageView = itemView.findViewById(R.id.upcoming_lawyer_profile)
        val upcomingSessionDate: TextView = itemView.findViewById(R.id.upcoming_session_date)
        val upcomingSessionTime: TextView = itemView.findViewById(R.id.upcoming_session_time)
        val upcomingCancel: TextView = itemView.findViewById(R.id.upcoming_cancel_button)
        val upcomingReschedule: TextView = itemView.findViewById(R.id.upcoming_reschedule_button)

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
            .inflate(R.layout.upcoming_appointment_items, viewGroup, false)
        return MyViewHolder(v)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val currentItem = list[i]
        viewHolder.upcomingLawyerName.text = currentItem.advocateName
        //viewHolder.bookedLawyerExpert.text = currentItem.sessionTime
        //val date = getDateFormatted(currentItem.sessionDate)
        viewHolder.upcomingSessionDate.text = getDateFormatted(currentItem.sessionDate)
        viewHolder.upcomingSessionTime.text = currentItem.sessionTime
    }

    override fun getItemCount(): Int {
        return list.size
    }
}