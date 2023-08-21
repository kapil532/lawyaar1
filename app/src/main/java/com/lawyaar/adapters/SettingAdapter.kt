package com.lawyaar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.utils.TalkListner

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    private val settingIcons = arrayOf(
        R.drawable.user,
        R.drawable.messenger,
        R.drawable.privacy,
        R.drawable.faq,
        R.drawable.lang,
        R.drawable.rate_us,
        R.drawable.user
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemsName: TextView
        val itemIcon: ImageView

        init {
            itemsName = view.findViewById(R.id.txt_items_name)
            itemIcon = view.findViewById(R.id.item_icon)
        }
    }

    var dataSet = arrayOf("")
    fun setUpdateData(dataSet: Array<String>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_list_items, parent, false)
        return ViewHolder(view)
    }

    private lateinit var talkListener: TalkListner

    fun setUplistner(talkListener: TalkListner) {
        this.talkListener = talkListener
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemsName.text = dataSet[position]
        holder.itemIcon.setImageResource(settingIcons[position])
        holder.itemView.setOnClickListener {
            talkListener.onTalkClickListner(dataSet[position])
        }
    }
}