package com.lawyaar.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
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
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModel
import com.lawyaar.models.lawyer_search.post_details.PostFilter
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchFactoyModel
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchViewModel
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.ui.payment_screen.PaymentActivity
import com.lawyaar.retrofit.LawyaarApi
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.auth.OTPActivity
import com.lawyaar.ui.book_slots.BookingSlotActivity
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.FilterOption
import com.lawyaar.utils.TalkListner
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class HomeFragment : Fragment(), CellClickListener, TalkListner ,FilterOption {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: LawyaarAdapter
    lateinit var lawyerSearchViewModel: LawyerSearchViewModel

    @Inject
    lateinit var lawyerSearchFactoyModel: LawyerSearchFactoyModel
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
        initNetwork()
        adapter.setUplistner(this, this)
        MainActivity.filterOption =this
        return veiw
    }

    var tokenValue =""
    @SuppressLint("FragmentLiveDataObserve", "SuspiciousIndentation")
    fun initNetwork() {


        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)
         tokenValue = sharedPreferences.getString("token_val", " ").toString()

        (activity?.application as LawyaarApplication).applicationComponent.inject(homeFragment = this)
        lawyerSearchViewModel =
            ViewModelProvider(this, lawyerSearchFactoyModel).get(LawyerSearchViewModel::class.java)

        val languages: ArrayList<String> = ArrayList()
        val caseCategories: ArrayList<String> = ArrayList()
        val locations: ArrayList<String> = ArrayList()
        val lawyerCategories: ArrayList<String> = ArrayList()
        val offerPriceRange: ArrayList<Int> = ArrayList()
        val actualPriceRange: ArrayList<Int> = ArrayList()

        languages.add("7e566317-1984-48b8-be97-c77a41c43311")
        languages.add("7e566317-1984-48b8-be97-c77a41c43322")
        languages.add("7e566317-1984-48b8-be97-c77a41c43333")
//
        caseCategories.add("7a5ac650-08d2-46be-b44e-c0829e20e111")
        caseCategories.add("6b1ea0bb-3448-4d75-9d8b-aec4f9349b00")
        caseCategories.add("279baded-f519-47d5-ae4d-ca892a769577")
//
        locations.add("92e33e89-a438-46a6-8eb7-944f0a541d11")
        locations.add("92e33e89-a438-46a6-8eb7-944f0a541d22")
        locations.add("92e33e89-a438-46a6-8eb7-944f0a541d33")
//
//        lawyerCategories.add("e03da15f-8b87-4cab-87a5-824baca0981")
//        lawyerCategories.add("e03da15f-8b87-4cab-87a5-824baca09822")
//
        offerPriceRange.add(550)
        offerPriceRange.add(1650)
        actualPriceRange.add(650)
        actualPriceRange.add(1500)


        var postFilter = PostDataFilter(actualPriceRange,caseCategories,languages,lawyerCategories,locations,offerPriceRange)

        if (tokenValue != null)
        {
            Log.d("NOFOUND","NO LAWWAY -- > "+tokenValue)
            lawyerSearchViewModel.lawyerSearchByFilter(
                tokenValue,
                "language,category,locations",
                postFilter
            )
        }
        lawyerSearchViewModel.searchLawyerLiveData.observe(this, Observer<LawyerSearchModel>
        {
            if (it != null) {
                shimmer_view_container.hideShimmer()
                shimmer_view_container.visibility = View.GONE
                adapter.setUpdateData(it)
            }
            else{
                Log.d("NOFOUND","NO LAWWAY")
            }
        })


        // homeViewModel.quotes.observe(this, Observer<QuoteList> {
        //   if (it != null) {
        //    shimmer_view_container.hideShimmer()
        //    shimmer_view_container.visibility = View.GONE
        //    adapter.setUpdateData(it.results)
        //  }
        //   })

    }


    override fun onCellClickListener(userId :String) {
        //startActivity(Intent(context, LawyaarDetailsActivity::class.java))
        val intent = Intent(context, LawyaarDetailsActivity::class.java)
        intent.putExtra("userId" , userId)
        startActivity(intent)
    }

    override fun onTalkClickListner() {
        // initPayment(""+10)
        startActivity(Intent(activity, BookingSlotActivity::class.java))

    }

    override fun updateLawyaarDetails(postDataFilter: PostDataFilter)
    {
        ModelPreferencesManager.put(postDataFilter,"FILTER_DETAILS")
        if (tokenValue != null)
        {
            Log.d("NOFOUND","NO LAWWAY -- > "+tokenValue)
            lawyerSearchViewModel.lawyerSearchByFilter(
                tokenValue,
                "language,category,locations",
                postDataFilter
            )
        }
        lawyerSearchViewModel.searchLawyerLiveData.observe(this, Observer<LawyerSearchModel>
        {
            if (it != null) {
                shimmer_view_container.hideShimmer()
                shimmer_view_container.visibility = View.GONE
                adapter.setUpdateData(it)
            }
            else{
                Log.d("NOFOUND","NO LAWWAY")
            }
        })

    }


}