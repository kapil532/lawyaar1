package com.lawyaar.ui.wallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.PriceAdapter
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.book_slots.adaptors.BookingTimeAdaper
import com.lawyaar.ui.payment_screen.PaymentActivity
import com.lawyaar.utils.TalkListner

class AddPointsInWallet : BaseActivity() ,TalkListner{
    lateinit var recyclerPriceSlots: RecyclerView
    lateinit var wallet_balance_edit_text: EditText
    private lateinit var points: String
    private lateinit var adapter: PriceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_points_in_wallet_layout)
        val back_icon = findViewById<ImageView>(R.id.back_icon)

        points = intent.getStringExtra("points").toString()

        val wallet_balance = findViewById<TextView>(R.id.wallet_balance)
        wallet_balance.setText("\u20B9 "+ points)
        val proceed_pay = findViewById<Button>(R.id.proceed_pay)
        proceed_pay.setOnClickListener({
            proceedToPay()
        })
        recyclerPriceSlots = findViewById<RecyclerView>(R.id.prices_list)
        wallet_balance_edit_text = findViewById<EditText>(R.id.wallet_balance_edit_text)



        back_icon.setOnClickListener({
            finish()
        })
        initRecyle()
    }


    fun initRecyle() {
        val arrtime = arrayOf(
            "99",
            "199",
            "299",
            "399",
            "499",
            "699",
            "1000",
            "2000"
        )
        val layoutManager = GridLayoutManager(this, 4)
        recyclerPriceSlots.layoutManager = layoutManager

        adapter = PriceAdapter()
        recyclerPriceSlots.adapter = adapter
        adapter.setUplistner(this)
        adapter.setUpdateData(arrtime)
    }

    fun proceedToPay() {
        if (wallet_balance_edit_text.text.length > 1)
        {

            val intent =Intent(this@AddPointsInWallet, PaymentActivity::class.java)
            intent.putExtra("points" , ""+wallet_balance_edit_text.text)
            startActivity(intent)
           // startActivity(Intent(this@AddPointsInWallet, PaymentActivity::class.java))
        }
        else{
            Toast.makeText(this,"Please put the proper amount!",Toast.LENGTH_LONG).show()
        }
    }

    override fun onTalkClickListner(userId: String) {
       wallet_balance_edit_text.setText(userId)
    }

}