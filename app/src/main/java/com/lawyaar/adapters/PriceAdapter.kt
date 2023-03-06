package com.lawyaar.adapters

import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.utils.TalkListner

class PriceAdapter() : RecyclerView.Adapter<PriceAdapter.ViewHolder>() {

    val selectedItems = SparseBooleanArray()

    class ViewHolder(veiw: View) : RecyclerView.ViewHolder(veiw) {
        val date_slots_text: TextView
        val slots_available_down_date: TextView
        val item_card_veiw: CardView

        init {
            // Define click listener for the ViewHolder's View
            date_slots_text = veiw.findViewById(R.id.date_slots_text)
            slots_available_down_date = veiw.findViewById(R.id.slots_available_down_date)
            item_card_veiw = veiw.findViewById(R.id.item_card_veiw)

        }
    }


    var dataSet = arrayOf("")

    fun setUpdateData(dataSet: Array<String>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    private lateinit var talkListner: TalkListner

    fun setUplistner( talkListner: TalkListner)
    {
        this.talkListner= talkListner

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_time_booking_slot, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.date_slots_text.text = dataSet[position]
//             holder.slots_available_down _date.text = dataSet[position]
        holder.itemView.setOnClickListener({

            if(talkListner != null)
            {
                talkListner.onTalkClickListner(dataSet[position])
            }
            if (selectedItems.size() > 0) {

                selectedItems.clear()
                toggleSelection(position)

            } else {
                toggleSelection(position)
            }

        })

        if (selectedItems[position]) {
            holder.item_card_veiw.getBackground().setTint(Color.parseColor("#31bcf4"))
            holder.date_slots_text.setTextColor(Color.WHITE)
        } else {
            holder.item_card_veiw.getBackground().setTint(Color.WHITE)
            holder.date_slots_text.setTextColor(Color.GRAY)
        }
    }

    fun toggleSelection(position: Int) {
        selectedItems.put(position, !selectedItems[position])
        notifyDataSetChanged()
    }
    var i = 0;
    fun getSelectedTime() :String
    {
        i = 0;
        for (selectedItem in dataSet) {

            if (selectedItems[i]) {
               return  dataSet.get(i)
            }

            i++
        }
        return ""
    }

}