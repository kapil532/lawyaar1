package com.lawyaar.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.PriceAdapter
import com.lawyaar.adapters.SettingAdapter
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.payment_screen.PaymentActivity
import com.lawyaar.ui.profile.UpdateProfileActivity
import com.lawyaar.utils.TalkListner

class SettingActivity : BaseActivity(), TalkListner {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.setting_layout)
        recyclerPriceSlots = findViewById<RecyclerView>(R.id.prices_list)
        val back_icon = findViewById<ImageView>(R.id.back_icon)
        back_icon.setOnClickListener({
            finish()
        })

        initRecyle()
    }

    lateinit var recyclerPriceSlots: RecyclerView
    private lateinit var adapter: SettingAdapter

    fun initRecyle() {
        val arrtime = arrayOf(
            "Account",
            "Notifications",
            "Privacy",
            "FAQ",
            "Language",
            "Rate Us",
            "About",

            )
        val layoutManager = LinearLayoutManager(this)
        recyclerPriceSlots.layoutManager = layoutManager

        adapter = SettingAdapter()
        recyclerPriceSlots.adapter = adapter
        adapter.setUplistner(this)
        adapter.setUpdateData(arrtime)
    }

    override fun onTalkClickListner(userId: String) {
        when (userId) {
            "Account"-> updateProfile()
            "Notifications"-> updateProfile()
            "Privacy"-> updateProfile()
            "FAQ"-> updateProfile()
            "Language"-> updateProfile()
            "Rate Us"-> openPlayStore()
            "About"-> updateProfile()


        }


    }
  fun updateProfile()
  {
      startActivity(Intent(this@SettingActivity,UpdateProfileActivity::class.java))
  }

    fun openPlayStore() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}