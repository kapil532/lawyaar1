package com.lawyaar.payment_screen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lawyaar.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() , PaymentResultListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          setContentView(R.layout.payment_screen)
              initPayment("10")

    }


    fun initPayment(amount : String)
    {
        val amt =amount
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
       finish()
    }

    override fun onPaymentError(p0: Int, p1: String?)
    {

        Toast.makeText(applicationContext, "Please try again!", Toast.LENGTH_SHORT).show()

    }


}