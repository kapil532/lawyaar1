package com.lawyaar.ui.wallet

import android.R.attr.phoneNumber
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.WalletAdaptor
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.user_details.UserDetailsModel
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsFactoryModel
import com.lawyaar.models.user_details.user_details_view_model.UserDetailsViewModel
import com.lawyaar.models.wallet_details.AddWalletFactoryModel
import com.lawyaar.models.wallet_details.AddWalletViewModel
import com.lawyaar.models.wallets.WalletModel
import com.lawyaar.models.wallets.WalletModelItem
import com.lawyaar.models.wallets.veiw_model.WalletModelFactory
import com.lawyaar.models.wallets.veiw_model.WalletViewModel
import com.lawyaar.models.wallets.wallet_post_pojo.WalletPostBody
import com.lawyaar.ui.payment_screen.PaymentActivity
import javax.inject.Inject


class WalletActivity : AppCompatActivity() {


    lateinit var addWalletViewModel: AddWalletViewModel
    lateinit var walletViewModel: WalletViewModel

    @Inject
    lateinit var addWalletFactoryModel: AddWalletFactoryModel

    @Inject
    lateinit var walletModelFactory: WalletModelFactory

    lateinit var user_name :TextView
    lateinit var user_mobileno :TextView
    lateinit var wallet_balance :TextView
  //  lateinit var filter_recyle_langauge :RecyclerView
    lateinit var adaptor : WalletAdaptor

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wallet)
        val payment_button = findViewById<Button>(R.id.appoint_button)
        val back_icon = findViewById<ImageView>(R.id.back_icon)
         user_name = findViewById<TextView>(R.id.user_name)
       val wallet_recycle = findViewById<RecyclerView>(R.id.wallet_recycle)
        user_mobileno = findViewById<TextView>(R.id.user_mobileno)
        wallet_balance = findViewById<TextView>(R.id.wallet_balance)


        back_icon.setOnClickListener({
            finish()
        })
        payment_button.setOnClickListener({
            val intent = Intent(this@WalletActivity, AddPointsInWallet::class.java)
            intent.putExtra("points" , ""+points)
            startActivity(intent)
           // startActivity(Intent(this@WalletActivity, PaymentActivity::class.java))
        })
        wallet_recycle.layoutManager = LinearLayoutManager(this)
        adaptor = WalletAdaptor()
        wallet_recycle.adapter = adaptor

        initNetwork()
        getWalletDetails()
    }

    override fun onStart() {
        super.onStart()


    }
    var user_id = ""
    var tokenValue = ""
    fun initNetwork() {

        (application as LawyaarApplication).applicationComponent.inject(this)
        val sharedPreferences: SharedPreferences =
            application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        user_id = sharedPreferences.getString("user_id", " ").toString()




//        addWalletViewModel =
//            ViewModelProvider(this, addWalletFactoryModel).get(AddWalletViewModel::class.java)
//        addWalletViewModel.addWalletPoints(tokenValue, user_id, "100")
//        addWalletViewModel.addWalletLD.observe(this, Observer {
//
//            if (it != null) {
//                Log.d("ADPIONTS", "points --> " + it.userId)
//                wallet_balance.setText(""+it.point)
//            }
//        })


    }


    fun getWalletDetails() {

        val amountRange: ArrayList<Int> = ArrayList()
        val dateRange: ArrayList<Long> = ArrayList()

        amountRange.add(3)
        amountRange.add(1500)

//        dateRange.add(1676472242335)
//        dateRange.add(1676480011194)
        Log.d("USRID", "USERIDDD -- > " + user_id)
        val walletPojo = WalletPostBody(0, amountRange, dateRange, 0, user_id)
        walletViewModel =
            ViewModelProvider(this, walletModelFactory).get(WalletViewModel::class.java)
        walletViewModel.getWalletsList(tokenValue, user_id, walletPojo)

        walletViewModel.wallet.observe(this, Observer<WalletModel> {
            if (it != null) {
                if(it.size >0)
                {
                    adaptor.setUpdateData(it)
                    getValue(it)
                }


            }
        })
        getDetails()
    }
    var points : Int =0
    fun getValue(quoteList: ArrayList<WalletModelItem>)
    {

        for(value in quoteList)
        {
            points += value.point
        }
        wallet_balance.setText("\u20B9 "+ points)
    }

    lateinit var userDetailsViewModel: UserDetailsViewModel
    @Inject
    lateinit var userDetailsFactoryModel: UserDetailsFactoryModel


    fun getDetails() {
        userDetailsViewModel =
            ViewModelProvider(this, userDetailsFactoryModel).get(UserDetailsViewModel::class.java)
        if (tokenValue != null && user_id != null) {

            userDetailsViewModel.getUserDetails(tokenValue, "language,category,locations", user_id)
        }
        userDetailsViewModel.getUserLiveData.observe(this, Observer<UserDetailsModel> {
            if (it != null) {
                updateDetails(it)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })
    }
    @SuppressLint("SetTextI18n")
    fun updateDetails(userDetailsModel: UserDetailsModel)
    {
        val lengthMo = userDetailsModel.mobile.length
        val lastFourDigits: String =  userDetailsModel.mobile.substring( userDetailsModel.mobile.length - 4)
        user_mobileno.setText("**** **** "+lastFourDigits)
//        user_email.setText(userDetailsModel.email)
        user_name.setText("Welcome Back \n"+userDetailsModel.name)

    }

}