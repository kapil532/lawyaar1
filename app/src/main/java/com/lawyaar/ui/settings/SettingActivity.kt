package com.lawyaar.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.SettingAdapter
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.profile.UpdateProfileActivity
import com.lawyaar.utils.TalkListner

class SettingActivity : BaseActivity(), TalkListner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_layout)
        recyclerPriceSlots = findViewById(R.id.prices_list)
        val backIcon = findViewById<ImageView>(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }
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
            "Account" -> updateProfile()
            "Notifications" -> updateProfile()
            "Privacy" -> updateProfile()
            "FAQ" -> updateProfile()
            "Language" -> updateProfile()
            "Rate Us" -> openPlayStore()
            "About" -> updateProfile()
        }
    }

    private fun updateProfile() {
        startActivity(Intent(this@SettingActivity, UpdateProfileActivity::class.java))
    }

    private fun openPlayStore() {
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