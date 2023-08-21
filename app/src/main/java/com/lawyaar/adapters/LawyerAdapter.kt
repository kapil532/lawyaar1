package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.lawyaar.R
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.TalkListner

class LawyerAdapter : RecyclerView.Adapter<LawyerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lawyerProfile: ShapeableImageView = itemView.findViewById(R.id.lawyer_profile)
        val lawyerName: TextView = itemView.findViewById(R.id.lawyer_name)
        val lawyerRating: TextView = itemView.findViewById(R.id.lawyer_rating)
        val lawyerExpert: TextView = itemView.findViewById(R.id.lawyer_expert)
        val lawyerMore: ImageView = itemView.findViewById(R.id.lawyer_more)
    }

    var list = ArrayList<LawyerSearchModelItem>()
    fun setUpdateData(list: ArrayList<LawyerSearchModelItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun localSearchData(searchText: CharSequence?, lawyerList: ArrayList<LawyerSearchModelItem>) {
        val searchList = ArrayList<LawyerSearchModelItem>()
        for (i in lawyerList.indices) {
            if (lawyerList[i].name.uppercase().contains(searchText.toString().uppercase())) {
                val searchModel = LawyerSearchModelItem(
                    lawyerList[i].advocateDetail,
                    lawyerList[i].businessTitle,
                    lawyerList[i].caseCategories,
                    lawyerList[i].email,
                    lawyerList[i].emailVerified,
                    lawyerList[i].firebaseId,
                    lawyerList[i].languages,
                    lawyerList[i].mobile,
                    lawyerList[i].name,
                    lawyerList[i].userId,
                    lawyerList[i].username
                )
                searchList.add(searchModel)
            }
        }
        list = if (searchText?.isEmpty() == false) {
            searchList
        } else {
            lawyerList
        }
        notifyDataSetChanged()
    }

    private lateinit var cellClickListener: CellClickListener
    private lateinit var talkListener: TalkListner

    fun setUpListener(cellClickListener: CellClickListener, talkListener: TalkListner) {
        this.cellClickListener = cellClickListener
        this.talkListener = talkListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.lawyer_items, viewGroup, false)
        return MyViewHolder(v)
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val currentItem = list[i]
        viewHolder.lawyerName.text = currentItem.name
        viewHolder.lawyerRating.text = "4.9 (110 Reviews)"
        viewHolder.lawyerExpert.text = "Criminal and Civil"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}