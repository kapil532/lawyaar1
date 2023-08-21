package com.lawyaar.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.SettingAdapter
import com.lawyaar.ui.profile.UpdateProfileActivity
import com.lawyaar.utils.TalkListner

class SettingFragment : Fragment(), TalkListner {
    private lateinit var adapter: SettingAdapter
    lateinit var recycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_fragment, container, false)
        recycleView = view.findViewById(R.id.setting_list)
        setSettingItemsList()
        return view
    }

    private fun setSettingItemsList() {
        val settingListName = arrayOf(
            "Account",
            "Notifications",
            "Privacy",
            "FAQ",
            "Language",
            "Rate Us",
            "About",
        )
        val layoutManager = LinearLayoutManager(context)
        recycleView.layoutManager = layoutManager
        adapter = SettingAdapter()
        recycleView.adapter = adapter
        adapter.setUplistner(this)
        adapter.setUpdateData(settingListName)
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
        startActivity(Intent(context, UpdateProfileActivity::class.java))
    }

    private fun openPlayStore() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context?.packageName}")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${context?.packageName}")
                )
            )
        }
    }
}