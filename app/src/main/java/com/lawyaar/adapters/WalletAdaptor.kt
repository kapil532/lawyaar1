package com.lawyaar.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R

import com.lawyaar.models.wallets.WalletModelItem

class WalletAdaptor() : RecyclerView.Adapter<WalletAdaptor.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rs_wallet: TextView
        val re_title: TextView

        init {
            rs_wallet = view.findViewById(R.id.rs_wallet)
            re_title = view.findViewById(R.id.re_title)

        }
    }

    var list = ArrayList<WalletModelItem>()

    fun setUpdateData(quoteList: ArrayList<WalletModelItem>) {
        this.list = quoteList
        Log.d("--->","-- > "+quoteList.get(0).point+"--"+list.size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_wallet_transactiont, parent, false)

        return WalletAdaptor.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("--->","-- > "+list.get(position).point)

       holder.rs_wallet.text = "+ "+list.get(position).point
       holder.re_title.text = "Money Added "
    }



}