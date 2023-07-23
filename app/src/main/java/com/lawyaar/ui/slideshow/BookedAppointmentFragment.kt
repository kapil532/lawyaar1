package com.lawyaar.ui.slideshow

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.lawyaar.R
import com.lawyaar.adapters.BookedSessionAdapter
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.booked_session.BookedSessionModelItem
import com.lawyaar.models.booked_session.view_models.BookedSessionFactoryModel
import com.lawyaar.models.booked_session.view_models.BookedSessionViewModel
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.BookedSessionCallBack
import javax.inject.Inject

class BookedAppointmentFragment : Fragment(), BookedSessionCallBack {
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    private lateinit var adapter: BookedSessionAdapter
    lateinit var bookedSessionViewModel: BookedSessionViewModel

    @Inject
    lateinit var bookedSessionFactoryModel: BookedSessionFactoryModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_booked_session, container, false)
        val recycleView = view.findViewById<RecyclerView>(R.id.recycle_veiw)
        shimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        recycleView.layoutManager = LinearLayoutManager(activity)
        adapter = BookedSessionAdapter()
        recycleView.adapter = adapter
        initNetwork()
        adapter.setUplistner(this)
        return view
    }

    var userId = ""
    var tokenValue = ""
    fun initNetwork() {
        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        userId = sharedPreferences.getString("user_id", " ").toString()
        (activity?.application as LawyaarApplication).applicationComponent.inject(
            bookedAppointmentFragment = this
        )

        bookedSessionViewModel =
            ViewModelProvider(this, bookedSessionFactoryModel)[BookedSessionViewModel::class.java]
        bookedSessionViewModel.getBookedSession(
            tokenValue,
            "language,case,category,location",
            userId
        )
        bookedSessionViewModel.bookedSessionLD.observe(viewLifecycleOwner) {
            if (it != null) {
                shimmerViewContainer.hideShimmer()
                shimmerViewContainer.visibility = View.GONE
                adapter.setUpdateData(it)
                Log.d("NO FOUND", "NO LAW-WAY$it")
            } else {
                Log.d("NO FOUND", "NO LAW-WAY")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCellClickListener(bookedSessionModelItem: BookedSessionModelItem) {
        val intent = Intent(context, LawyaarDetailsActivity::class.java)
        startActivity(intent)
    }
}