package com.lawyaar.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.MainActivity
import com.lawyaar.R
import com.lawyaar.testlist.QuoteList
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.TalkListner

class LawyaarAdapter() : RecyclerView.Adapter<LawyaarAdapter.MyVeiwHolder>() {

//    var quoteList = QuoteList(0,0,0, ,0,0)


   class MyVeiwHolder(itemVeiw : View) :RecyclerView.ViewHolder(itemVeiw)
   {

       val lawyaaricon : ImageView = itemVeiw.findViewById(R.id.lawaar_icon)

       val lawyaarname : TextView = itemVeiw.findViewById(R.id.lawyaar_name)
       val lawyaar_more : TextView = itemVeiw.findViewById(R.id.lawyaar_more)
       val book_button : TextView = itemVeiw.findViewById(R.id.book_button)

   }

    var list = ArrayList<com.lawyaar.testlist.Result>()

    fun setUpdateData(quoteList: ArrayList<com.lawyaar.testlist.Result>)
    {
        this.list =  quoteList
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
            .inflate(R.layout.lawyaar_single_list, viewGroup, false)
        return MyVeiwHolder(v)
    }


    override fun onBindViewHolder(viewHolder: MyVeiwHolder, i: Int) {
        val currentitem = list.get(i)
        viewHolder.lawyaarname.text = currentitem.author

        viewHolder.lawyaar_more.setOnClickListener({
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