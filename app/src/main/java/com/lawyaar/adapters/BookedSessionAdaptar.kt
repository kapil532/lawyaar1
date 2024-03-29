package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.booked_session.BookedSessionModelItem
import com.lawyaar.models.booked_session.CaseCategory
import com.lawyaar.models.lawyaar_details_transfer.LawyaarTransfer
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.utils.BookedSessionCallBack
import java.text.SimpleDateFormat

class BookedSessionAdaptar() : RecyclerView.Adapter<BookedSessionAdaptar.MyVeiwHolder>() {



   class MyVeiwHolder(itemVeiw : View) :RecyclerView.ViewHolder(itemVeiw)
   {
       val lawyaarname : TextView = itemVeiw.findViewById(R.id.lawyaar_name)
       val lawyaar_exper : TextView = itemVeiw.findViewById(R.id.lawyaar_exper)
       val lawyaaricon : ImageView = itemVeiw.findViewById(R.id.lawaar_icon)
       val lawyaar_lang : TextView = itemVeiw.findViewById(R.id.lawyaar_lang)
       val book_button : TextView = itemVeiw.findViewById(R.id.book_button)
       val session_time : TextView = itemVeiw.findViewById(R.id.session_time)
       val session_date : TextView = itemVeiw.findViewById(R.id.session_date)

   }

    var list = ArrayList<BookedSessionModelItem>()

        fun setUpdateData(list: ArrayList<BookedSessionModelItem>)
    {
        this.list =  list
        notifyDataSetChanged()
    }


    private lateinit var cellClickListener: BookedSessionCallBack


    fun setUplistner(cellClickListener: BookedSessionCallBack)
    {
        this.cellClickListener= cellClickListener

    }
    @SuppressLint("SimpleDateFormat")
    fun dateFormatter(milliseconds: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = inputFormat.parse(milliseconds)
        val formattedDate = outputFormat.format(date)
        return  (formattedDate)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyVeiwHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.single_row_booked_session_layout, viewGroup, false)
        return MyVeiwHolder(v)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(viewHolder: MyVeiwHolder, i: Int) {
        val currentitem = list.get(i)
        viewHolder.lawyaarname.text = currentitem.name
        viewHolder.session_time.text =currentitem.sessionTime
        viewHolder.session_date.text = (currentitem.sessionDate)
        var  caseS =""
        var  langS =""
               if(currentitem.caseCategories.size  >0) {

                   for ( case in currentitem.caseCategories )
                   {
                     caseS += " "+case.name
                   }
                   for ( lang in currentitem.languages )
                   {
                       langS += " "+lang.name
                   }
               }
        viewHolder.lawyaar_lang.text = langS
        viewHolder.lawyaar_exper.text = ""+caseS

        viewHolder.itemView.setOnClickListener({

           if(currentitem != null) {
               val lawyaarTransfer = LawyaarTransfer(currentitem.advocateDetailId,currentitem.advocateId, (currentitem.caseCategories) as (List<com.lawyaar.models.lawyer_search.post_details.CaseCategory>) ,(currentitem.languages) as (List<com.lawyaar.models.lawyer_search.post_details.Language>),currentitem.name)
               ModelPreferencesManager.put(lawyaarTransfer,"LAWYAR_DETAILS")
               cellClickListener.onCellClickListener(currentitem)
           }

        })

        viewHolder.book_button.setOnClickListener({

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }


}