package com.lawyaar.ui.slideshow

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.lawyaar.R
import com.lawyaar.adapters.BookedSessionAdapter
import com.lawyaar.adapters.CancelledAppointmentAdapter
import com.lawyaar.adapters.UpcomingAppointmentAdapter
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.core.constants.API_HEADER_ADVOCATE_USER
import com.lawyaar.core.constants.CANCELLED
import com.lawyaar.core.constants.COMPLETED
import com.lawyaar.core.constants.OPEN
import com.lawyaar.core.constants.RESCHEDULED
import com.lawyaar.core.constants.SHARED_PREF_AUTH_TOKEN
import com.lawyaar.core.constants.SHARED_PREF_AUTH_TOKEN_VALUE
import com.lawyaar.core.constants.SHARED_PREF_USER_ID
import com.lawyaar.models.booked_session.BookedSessionModelItem
import com.lawyaar.models.booked_session.view_models.BookedSessionFactoryModel
import com.lawyaar.models.booked_session.view_models.BookedSessionViewModel
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.BookedSessionCallBack
import javax.inject.Inject

class BookedAppointmentFragment : Fragment(), BookedSessionCallBack {
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    private lateinit var upcomingAdapter: UpcomingAppointmentAdapter
    private lateinit var bookedAdapter: BookedSessionAdapter
    private lateinit var cancelAdapter: CancelledAppointmentAdapter
    lateinit var bookedSessionViewModel: BookedSessionViewModel
    private lateinit var txtUpcomingAppointment: TextView
    private lateinit var txtCompletedAppointment: TextView
    private lateinit var txtCancelAppointment: TextView
    companion object {
        private const val UPCOMING_APPOINT = "Upcoming"
        private const val COMPLETED_APPOINT = "Completed"
        private const val CANCEL_APPOINT = "Cancel"
    }

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
        initIds(view)
        setUpcomingAppointmentByDefault(view, recycleView)
        val txtHeader = view.findViewById<TextView>(R.id.txt_header)
        txtHeader.text = getString(R.string.header_appointment)
        onUpcomingAppointment(view, recycleView)
        onCompletedAppointment(view, recycleView)
        onCancelAppointment(view, recycleView)
        return view
    }

    private fun initIds(view: View){
        txtUpcomingAppointment = view.findViewById(R.id.upcoming_section)
        txtCompletedAppointment = view.findViewById(R.id.completed_section)
        txtCancelAppointment = view.findViewById(R.id.cancel_section)
    }

    private fun setUpcomingAppointmentByDefault(view: View, recycleView: RecyclerView){
        val sessionStatusList = mutableListOf<String>().apply {
            add(RESCHEDULED)
            add(OPEN)
            add(CANCELLED)
            add(COMPLETED)
        }
        setViewVisibleGone(view, UPCOMING_APPOINT)
        upcomingAdapter = UpcomingAppointmentAdapter()
        recycleView.adapter = upcomingAdapter
        initNetwork(sessionStatusList)
        upcomingAppointmentObserver()
        upcomingAdapter.setUplistner(this)
    }

    private fun onUpcomingAppointment(view: View, recycleView: RecyclerView){
        val sessionStatusList = mutableListOf<String>().apply {
            add(RESCHEDULED)
            add(OPEN)
            add(CANCELLED)
            add(COMPLETED)
        }

        txtUpcomingAppointment.setOnClickListener {
            setViewVisibleGone(view, UPCOMING_APPOINT)
            upcomingAdapter = UpcomingAppointmentAdapter()
            recycleView.adapter = upcomingAdapter
            initNetwork(sessionStatusList)
            upcomingAppointmentObserver()
            upcomingAdapter.setUplistner(this)
        }
    }

    private fun onCompletedAppointment(view: View, recycleView: RecyclerView){
        val sessionStatusList = mutableListOf<String>().apply {
            add(RESCHEDULED)
            add(OPEN)
            add(CANCELLED)
            add(COMPLETED)
        }

        txtCompletedAppointment.setOnClickListener {
            setViewVisibleGone(view, COMPLETED_APPOINT)
            bookedAdapter = BookedSessionAdapter()
            recycleView.adapter = bookedAdapter
            initNetwork(sessionStatusList)
            bookedAppointmentObserver()
            bookedAdapter.setUplistner(this)
        }
    }

    private fun onCancelAppointment(view: View, recycleView: RecyclerView){
        val sessionStatusList = mutableListOf<String>().apply {
            add(RESCHEDULED)
            add(OPEN)
            add(CANCELLED)
            add(COMPLETED)
        }
        txtCancelAppointment.setOnClickListener {
            setViewVisibleGone(view, CANCEL_APPOINT)
            cancelAdapter = CancelledAppointmentAdapter()
            recycleView.adapter = cancelAdapter
            initNetwork(sessionStatusList)
            cancelAppointmentObserver()
            cancelAdapter.setUplistner(this)
        }
    }

    private fun setViewVisibleGone(view: View, viewType: String){
        val txtUpcomingView = view.findViewById<View>(R.id.upcoming_view)
        val txtCompletedView = view.findViewById<View>(R.id.completed_view)
        when (viewType) {
            UPCOMING_APPOINT -> {
                txtUpcomingAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.booked_appointment_status) }
                txtUpcomingAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                txtCompletedAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
                txtCancelAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
                txtCompletedAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.appointment_white_bg) }
                txtCancelAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.appointment_white_bg) }
                txtUpcomingView.visibility = View.GONE
                txtCompletedView.visibility = View.VISIBLE
            }
            COMPLETED_APPOINT -> {
                txtUpcomingAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.appointment_white_bg) }
                txtCompletedAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.booked_appointment_status)}
                txtUpcomingAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
                txtCompletedAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                txtCancelAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
                txtCancelAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.appointment_white_bg) }
                txtUpcomingView.visibility = View.GONE
                txtCompletedView.visibility = View.GONE

            }
            CANCEL_APPOINT -> {
                txtUpcomingAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.appointment_white_bg) }
                txtCompletedAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.appointment_white_bg) }
                txtCancelAppointment.background = activity?.let { ContextCompat.getDrawable(it, R.drawable.booked_appointment_status)}
                txtUpcomingAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
                txtCompletedAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
                txtCancelAppointment.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                txtUpcomingView.visibility = View.VISIBLE
                txtCompletedView.visibility = View.GONE
            }
        }
    }

    var userId = ""
    var tokenValue = ""
    fun initNetwork(sessionStatusList: List<String?>?) {

        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences(SHARED_PREF_AUTH_TOKEN, Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString(SHARED_PREF_AUTH_TOKEN_VALUE, " ").toString()
        userId = sharedPreferences.getString(SHARED_PREF_USER_ID, " ").toString()
        (activity?.application as LawyaarApplication).applicationComponent.inject(
            bookedAppointmentFragment = this
        )

        bookedSessionViewModel =
            ViewModelProvider(this, bookedSessionFactoryModel)[BookedSessionViewModel::class.java]
        bookedSessionViewModel.getBookedSession(
            tokenValue,
            API_HEADER_ADVOCATE_USER,
            userId,
            sessionStatusList
        )
    }

    private fun bookedAppointmentObserver() {
        bookedSessionViewModel.bookedSessionLD.observe(viewLifecycleOwner) {
            if (it != null) {
                shimmerViewContainer.hideShimmer()
                shimmerViewContainer.visibility = View.GONE
                bookedAdapter.setUpdateData(it)
            } else {
                Log.d("NO FOUND", "NO LAW-WAY")
            }
        }
    }

    private fun upcomingAppointmentObserver() {
        bookedSessionViewModel.bookedSessionLD.observe(viewLifecycleOwner) {
            if (it != null) {
                shimmerViewContainer.hideShimmer()
                shimmerViewContainer.visibility = View.GONE
                upcomingAdapter.setUpdateData(it)
            } else {
                Log.d("NO FOUND", "NO LAW-WAY")
            }
        }
    }

    private fun cancelAppointmentObserver() {
        bookedSessionViewModel.bookedSessionLD.observe(viewLifecycleOwner) {
            if (it != null) {
                shimmerViewContainer.hideShimmer()
                shimmerViewContainer.visibility = View.GONE
                cancelAdapter.setUpdateData(it)
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