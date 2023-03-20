package com.lawyaar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.ViewHolder>()
{

    val arrtime = arrayOf(
    R.drawable.user, R.drawable.messenger, R.drawable.privacy, R.drawable.faq, R.drawable.lang, R.drawable.rate_us, R.drawable.user

        )
    class ViewHolder(view : View) :RecyclerView.ViewHolder(view)
    {
        val single_loc_lan_title : TextView
        val left_icon : ImageView
        init {
            single_loc_lan_title   = view.findViewById(R.id.date_slots_text)
            left_icon   = view.findViewById(R.id.left_icon)
        }


    }

    var dataSet = arrayOf("")

    fun setUpdateData(dataSet: Array<String>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_setting, parent, false)
        return SettingAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
     return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.single_loc_lan_title.text = dataSet[position]
        holder.left_icon.setImageResource(arrtime[position])
        holder.itemView.setOnClickListener({

        })
    }


}