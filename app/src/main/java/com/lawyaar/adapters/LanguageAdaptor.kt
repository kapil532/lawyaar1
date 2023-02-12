package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.models.language.LanguageModelItem
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.location.LocationModelItem
import com.lawyaar.ui.book_slots.adaptors.BookingDateAdaptar

class LanguageAdaptor (): RecyclerView.Adapter<LanguageAdaptor.ViewHolder>()
{


    val selectedItems = SparseBooleanArray()
    val arrayList = ArrayList<String>()
     class ViewHolder(view :View) :RecyclerView.ViewHolder(view)
    {
        val single_loc_lan_title :TextView
        val item_card_veiw :CardView

           init {
               single_loc_lan_title   = view.findViewById(R.id.date_slots_text)
               item_card_veiw   = view.findViewById(R.id.item_card_veiw)

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
        holder.itemView.setOnClickListener({
            toggleSelection(position)
        })
        if (selectedItems[position])
        {
            holder.item_card_veiw.getBackground().setTint(Color.parseColor("#31bcf4"))
           holder.single_loc_lan_title.setTextColor(Color.WHITE)
        }
        else {
            holder.item_card_veiw.getBackground().setTint(Color.WHITE)
            holder.single_loc_lan_title.setTextColor(Color.GRAY)
        }
    }
     fun toggleSelection(position: Int) {
        selectedItems.put(position, !selectedItems[position])
        notifyItemChanged(position)
    }


    fun  getAllData()
    {


    }
}