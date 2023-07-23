package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.os.Build
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
import java.time.ZonedDateTime

class BookedSessionAdapter : RecyclerView.Adapter<BookedSessionAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookedLawyerName: TextView = itemView.findViewById(R.id.booked_lawyer_name)
        val bookedLawyerExpert: TextView = itemView.findViewById(R.id.booked_lawyer_expert)
        val bookedLawyerProfile: ImageView = itemView.findViewById(R.id.booked_lawyer_profile)
        val bookedSessionDate: TextView = itemView.findViewById(R.id.booked_session_date)
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
            .inflate(R.layout.booked_appointment_items, viewGroup, false)
        return MyViewHolder(v)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val currentItem = list[i]
        viewHolder.bookedLawyerName.text = currentItem.advocateName
        //viewHolder.bookedLawyerExpert.text = currentItem.sessionTime
        //val date = getDateFormatted(currentItem.sessionDate)
        viewHolder.bookedSessionDate.text = getDateFormatted(currentItem.sessionDate)
        /*var caseS = ""
        var langS = ""
        if (currentItem.caseCategories.size > 0) {

            for (case in currentItem.caseCategories) {
                caseS += " " + case.name
            }
            for (lang in currentItem.languages) {
                langS += " " + lang.name
            }
        }*/
        /*viewHolder.lawyaar_lang.text = langS
        viewHolder.lawyaar_exper.text = "" + caseS*/

        /*viewHolder.itemView.setOnClickListener {

           *//* val lawyaarTransfer = LawyaarTransfer(
                currentItem.advocateId ?: "",
                currentItem.advocateId ?: "",
                (currentitem.caseCategories) as (List<com.lawyaar.models.lawyer_search.post_details.CaseCategory>),
                (currentitem.languages) as (List<com.lawyaar.models.lawyer_search.post_details.Language>),
                currentitem.name
            )*//*
           // ModelPreferencesManager.put(lawyaarTransfer, "LAWYAR_DETAILS")
            cellClickListener.onCellClickListener(currentItem)

        }*/
    }
    override fun getItemCount(): Int {
        return list.size
    }
}