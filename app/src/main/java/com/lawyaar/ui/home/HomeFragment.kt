package com.lawyaar.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.lawyaar.MainActivity
import com.lawyaar.R
import com.lawyaar.adapters.LawyaarAdapter
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.ui.payment_screen.PaymentActivity
import com.lawyaar.retrofit.LawyaarApi
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.book_slots.BookingSlotActivity
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.TalkListner
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class HomeFragment : Fragment(), CellClickListener, TalkListner {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: LawyaarAdapter
   // lateinit var homeViewModel: HomeViewModel

   // @Inject
   // lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var shimmer_view_container: ShimmerFrameLayout


    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val veiw = inflater.inflate(R.layout.fragment_home, container, false)
        var recycle_veiw = veiw.findViewById<RecyclerView>(R.id.recycle_veiw)
        shimmer_view_container = veiw.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
        recycle_veiw.layoutManager = LinearLayoutManager(activity)
        adapter = LawyaarAdapter()
        recycle_veiw.adapter = adapter
        //initNetwork()
        adapter.setUplistner(this, this)
        return veiw
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun initNetwork() {
     //   (activity?.application as LawyaarApplication).applicationComponent.inject(homeFragment = this)
      //  homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

       // homeViewModel.quotes.observe(this, Observer<QuoteList> {
         //   if (it != null) {
            //    shimmer_view_container.hideShimmer()
            //    shimmer_view_container.visibility = View.GONE
            //    adapter.setUpdateData(it.results)
          //  }
     //   })

    }


    override fun onCellClickListener() {
        startActivity(Intent(context, LawyaarDetailsActivity::class.java))
    }

    override fun onTalkClickListner() {
        // initPayment(""+10)
        startActivity(Intent(activity, BookingSlotActivity::class.java))

    }

}