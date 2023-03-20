package com.lawyaar.ui.settings

import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.PriceAdapter
import com.lawyaar.adapters.SettingAdapter
import com.lawyaar.ui.base_screen.BaseActivity

class SettingActivity : BaseActivity()
{


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
        //adapter.setUplistner(this)
        adapter.setUpdateData(arrtime)
    }




}