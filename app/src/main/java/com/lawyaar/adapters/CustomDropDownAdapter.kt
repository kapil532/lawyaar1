package com.lawyaar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lawyaar.R
import com.lawyaar.models.case_category.CaseCategoryItem
import com.lawyaar.models.location.LocationModelItem

class CustomDropDownAdapter(val context: Context) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.single_row_time_booking_slo_no_backt, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataSource.get(position).name



        return view
    }
    var dataSource=ArrayList<CaseCategoryItem>()

    fun setUpdateData( dataSource:ArrayList<CaseCategoryItem>)
    {
        this.dataSource =  dataSource
        notifyDataSetChanged()
    }
    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView

        init {
            label = row?.findViewById(R.id.date_slots_text) as TextView
        }
    }

}