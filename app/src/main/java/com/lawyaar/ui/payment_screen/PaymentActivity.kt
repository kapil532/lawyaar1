package com.lawyaar.ui.payment_screen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.R
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.wallet_details.AddWalletFactoryModel
import com.lawyaar.models.wallet_details.AddWalletViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var points: String

    lateinit var addWalletViewModel: AddWalletViewModel

    @Inject
    lateinit var addWalletFactoryModel: AddWalletFactoryModel


    lateinit var recharge_wallet: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_screen)

        recharge_wallet = findViewById<TextView>(R.id.recharge_wallet)
        recharge_wallet.visibility = View.GONE
        points = intent.getStringExtra("points").toString()
        initPayment(points)

    }


    companion object
    {
        fun newIntent(activity: AppCompatActivity) : AppCompatActivity{
           return activity
        }

    }


    fun initPayment(amount: String) {
        val amt = amount
        // rounding off the amount.
        val amount = Math.round(amt.toFloat() * 100).toInt()
        val checkout = Checkout()
        // on the below line we have to see our id.
        checkout.setKeyID("rzp_test_GJV9Uoi7YR7m2S")
        // set image
        checkout.setImage(R.drawable.lawyaar_icon)
        val obj = JSONObject()
        try {
            obj.put("name", "Lawyaar.co")
            obj.put("description", "Test payment")
            obj.put("theme.color", "")
            obj.put("currency", "INR")
            obj.put("amount", amount)
            obj.put("prefill.contact", "8095128426")
            obj.put("prefill.email", "lawyaar@lawyaar.co")
            checkout.open(this@PaymentActivity, obj)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(applicationContext, "Thanks For Payment!", Toast.LENGTH_SHORT).show()
        recharge_wallet.visibility = View.VISIBLE
       // PaymentActivity.newIntent()
        initNetwork()
    }

    override fun onPaymentError(p0: Int, p1: String?) {

        Toast.makeText(applicationContext, "Please try again!", Toast.LENGTH_SHORT).show()
        finish()
    }

    var user_id = ""
    var tokenValue = ""
    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)
        val sharedPreferences: SharedPreferences =
            application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        user_id = sharedPreferences.getString("user_id", " ").toString()


        addWalletViewModel =
            ViewModelProvider(this, addWalletFactoryModel).get(AddWalletViewModel::class.java)
        addWalletViewModel.addWalletPoints(tokenValue, user_id, points)
        addWalletViewModel.addWalletLD.observe(this, Observer {

            if (it != null) {
                Log.d("ADPIONTS", "points --> " + it.userId)
                //wallet_balance.setText(""+it.point)
                finish()
            } else {
                Log.d("ADPIONTS", "points --> ELSE ")
                finish()
            }
        })


    }

}