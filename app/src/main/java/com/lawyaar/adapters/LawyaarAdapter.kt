package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.TalkListner

class LawyaarAdapter() : RecyclerView.Adapter<LawyaarAdapter.MyVeiwHolder>() {



   class MyVeiwHolder(itemVeiw : View) :RecyclerView.ViewHolder(itemVeiw)
   {
       val lawyaarname : TextView = itemVeiw.findViewById(R.id.lawyaar_name)
       val lawyaar_exper : TextView = itemVeiw.findViewById(R.id.lawyaar_exper)
       val lawyaaricon : ImageView = itemVeiw.findViewById(R.id.lawaar_icon)
       val lawyaar_lang : TextView = itemVeiw.findViewById(R.id.lawyaar_lang)
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
                     caseS += " "+case.name
                   }
                   for ( lang in currentitem.languages )
                   {
                       langS += " "+lang.name
                   }
               }
        viewHolder.lawyaar_lang.text = langS
        viewHolder.lawyaar_exper.text = ""+caseS

        viewHolder.lawyaaricon.setOnClickListener({
            cellClickListener.onCellClickListener()
        })

        viewHolder.book_button.setOnClickListener({
            talkListner.onTalkClickListner()
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }


}