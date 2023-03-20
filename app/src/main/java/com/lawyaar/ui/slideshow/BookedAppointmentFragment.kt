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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.lawyaar.R
import com.lawyaar.adapters.BookedSessionAdaptar
import com.lawyaar.adapters.LawyaarAdapter
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.models.book_session.view_model.BookingSessionFactoryModel
import com.lawyaar.models.booked_session.BookedSessionModel
import com.lawyaar.models.booked_session.BookedSessionModelItem
import com.lawyaar.models.booked_session.view_models.BookedSessionFactoryModel
import com.lawyaar.models.booked_session.view_models.BookedSessionViewModel
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchFactoyModel
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchViewModel
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.BookedSessionCallBack
import javax.inject.Inject

class BookedAppointmentFragment : Fragment() ,BookedSessionCallBack
{
    lateinit var shimmer_view_container: ShimmerFrameLayout
    private lateinit var adapter: BookedSessionAdaptar

    lateinit var bookedSessionViewModel: BookedSessionViewModel

    @Inject
    lateinit var bookedSessionFactoryModel: BookedSessionFactoryModel

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
       adapter.setUplistner(this)
        return veiw
    }
    var user_id = ""
    var tokenValue =""
    fun initNetwork()
    {
        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        user_id = sharedPreferences.getString("user_id", " ").toString()
        (activity?.application as LawyaarApplication).applicationComponent.inject(bookedAppointmentFragment=this)

        bookedSessionViewModel =
            ViewModelProvider(this, bookedSessionFactoryModel).get(BookedSessionViewModel::class.java)
        bookedSessionViewModel.getBookedSession(tokenValue,"language,case,category,location",user_id)
        bookedSessionViewModel.bookedSessionLD.observe(viewLifecycleOwner, Observer<BookedSessionModel>{
            if (it != null) {
                shimmer_view_container.hideShimmer()
                shimmer_view_container.visibility = View.GONE
                adapter.setUpdateData(it)
                Log.d("NOFOUND","NO LAWWAY"+it)
            }
            else{
                Log.d("NOFOUND","NO LAWWAY")
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCellClickListener(bookedSessionModelItem: BookedSessionModelItem)
    {
        val intent = Intent(context, LawyaarDetailsActivity::class.java)
        startActivity(intent)
    }
}