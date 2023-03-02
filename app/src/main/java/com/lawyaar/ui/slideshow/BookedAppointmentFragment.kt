package com.lawyaar.ui.slideshow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.lawyaar.R
import com.lawyaar.adapters.BookedSessionAdaptar
import com.lawyaar.adapters.LawyaarAdapter
import com.lawyaar.application.LawyaarApplication

class BookedAppointmentFragment : Fragment() {
    lateinit var shimmer_view_container: ShimmerFrameLayout
    private lateinit var adapter: BookedSessionAdaptar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val veiw= inflater.inflate(R.layout.fragment_booked_session,container,false)
        var recycle_veiw = veiw.findViewById<RecyclerView>(R.id.recycle_veiw)
        shimmer_view_container = veiw.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
        recycle_veiw.layoutManager = LinearLayoutManager(activity)
        adapter = BookedSessionAdaptar()
        recycle_veiw.adapter = adapter
        initNetwork()
      //  adapter.setUplistner(this, this)
        return veiw
    }
    var user_id = ""
    var tokenValue =""
    fun initNetwork()
    {
        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()

        (activity?.application as LawyaarApplication).applicationComponent.inject(bookedAppointmentFragment=this)


    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}