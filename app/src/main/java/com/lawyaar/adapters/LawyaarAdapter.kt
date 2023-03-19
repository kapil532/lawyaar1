package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.lawyaar_details_transfer.LawyaarTransfer
import com.lawyaar.models.lawyer_search.post_details.CaseCategory
import com.lawyaar.models.lawyer_search.post_details.Language
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.TalkListner

class LawyaarAdapter() : RecyclerView.Adapter<LawyaarAdapter.MyVeiwHolder>() {



   class MyVeiwHolder(itemVeiw : View) :RecyclerView.ViewHolder(itemVeiw)
   {
       val lawyaarname : TextView = itemVeiw.findViewById(R.id.lawyaar_name)
       val lawyaar_exper : TextView = itemVeiw.findViewById(R.id.lawyaar_exper)
       val lawyaar_exp : TextView = itemVeiw.findViewById(R.id.lawyaar_exp)
       val lawyaaricon : ImageView = itemVeiw.findViewById(R.id.lawaar_icon)
       val lawyaar_lang : TextView = itemVeiw.findViewById(R.id.lawyaar_lang)
       val lawyaar_price_actual : TextView = itemVeiw.findViewById(R.id.lawyaar_price_actual)
       val lawyaar_price_offer : TextView = itemVeiw.findViewById(R.id.lawyaar_price_offer)
       val book_button : TextView = itemVeiw.findViewById(R.id.book_button)

   }

    var list = ArrayList<LawyerSearchModelItem>()

        fun setUpdateData(list: ArrayList<LawyerSearchModelItem>)
    {
        this.list =  list
        notifyDataSetChanged()
    }


    private lateinit var cellClickListener: CellClickListener
    private lateinit var talkListner: TalkListner

    fun setUplistner(cellClickListener: CellClickListener ,talkListner: TalkListner)
    {
        this.cellClickListener= cellClickListener
        this.talkListner= talkListner

    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyVeiwHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.single_row_lawyer_layout, viewGroup, false)
        return MyVeiwHolder(v)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(viewHolder: MyVeiwHolder, i: Int) {
        val currentitem = list.get(i)
        viewHolder.lawyaarname.text = currentitem.name
        var  caseS =""
        var  langS =""
               if(currentitem.caseCategories.size  >0) {

                   for ( case in currentitem.caseCategories )
                   {
                     caseS += case.name+", "
                   }
                   for ( lang in currentitem.languages )
                   {
                       langS += lang.name+", "
                   }
               }

        val res_lang = langS.dropLast(2)
        val res_case = caseS.dropLast(2)
        viewHolder.lawyaar_lang.text = res_lang
        viewHolder.lawyaar_exper.text = ""+res_case
        viewHolder.lawyaar_exp.text = ""+currentitem.advocateDetail.experience+" experience"
        viewHolder.lawyaar_price_actual.text = ""+currentitem.advocateDetail.actualPrice +"/Session"
        viewHolder.lawyaar_price_actual.setPaintFlags(viewHolder.lawyaar_price_actual.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        viewHolder.lawyaar_price_offer.text = ""+currentitem.advocateDetail.offerPrice+"/Session"
      //

        viewHolder.lawyaaricon.setOnClickListener({
        Log.d("currentitem","--> "+currentitem.advocateDetail.advocateDetailId +" -- >"+currentitem.userId)

           if(currentitem != null) {
               val lawyaarTransfer = LawyaarTransfer(currentitem.advocateDetail.advocateDetailId,currentitem.userId, (currentitem.caseCategories)  ,(currentitem.languages),currentitem.name)
               ModelPreferencesManager.put(lawyaarTransfer,"LAWYAR_DETAILS")
             //  ModelPreferencesManager.put(currentitem,"LAWYAR_DETAILS")
               cellClickListener.onCellClickListener(currentitem)


           }

        })

        viewHolder.book_button.setOnClickListener({
            Log.d("currentitem","--> -- >"+currentitem.userId)

            talkListner.onTalkClickListner(currentitem.userId )
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }


}