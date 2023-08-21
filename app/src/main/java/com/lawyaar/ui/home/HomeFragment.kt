package com.lawyaar.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.lawyaar.HomeScreenActivity
import com.lawyaar.R
import com.lawyaar.adapters.HomeAdapter
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.core.constants.FILTER_TYPES
import com.lawyaar.core.constants.SHARED_PREF_AUTH_TOKEN
import com.lawyaar.core.constants.SHARED_PREF_AUTH_TOKEN_VALUE
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchFactoyModel
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchViewModel
import com.lawyaar.ui.book_slots.BookingSlotActivity
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.FilterOption
import com.lawyaar.utils.TalkListner
import javax.inject.Inject

class HomeFragment : Fragment(), CellClickListener, TalkListner, FilterOption {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: HomeAdapter
    lateinit var lawyerSearchViewModel: LawyerSearchViewModel

    @Inject
    lateinit var lawyerSearchFactoyModel: LawyerSearchFactoyModel
    lateinit var shimmer_view_container: ShimmerFrameLayout
    private var lawyerMasterList = ArrayList<LawyerSearchModelItem>()


    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val veiw = inflater.inflate(R.layout.fragment_home, container, false)
        val recycle_veiw = veiw.findViewById<RecyclerView>(R.id.recycle_veiw)
        shimmer_view_container = veiw.findViewById(R.id.shimmer_view_container)
        recycle_veiw.layoutManager = LinearLayoutManager(activity)
        adapter = HomeAdapter()
        recycle_veiw.adapter = adapter
        initNetwork()
        adapter.setUplistner(this, this)
        HomeScreenActivity.filterOption = this
        return veiw
    }

    var tokenValue = ""

    @SuppressLint("FragmentLiveDataObserve", "SuspiciousIndentation")
    fun initNetwork() {
        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences(SHARED_PREF_AUTH_TOKEN, Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString(SHARED_PREF_AUTH_TOKEN_VALUE, " ").toString()

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


        val postFilter = PostDataFilter(
            actualPriceRange,
            caseCategories,
            languages,
            lawyerCategories,
            locations,
            offerPriceRange
        )

        lawyerSearchViewModel.lawyerSearchByFilter(
            tokenValue,
            FILTER_TYPES,
            postFilter
        )
        lawyerSearchViewModel.searchLawyerLiveData.observe(this) {
            if (it != null) {
                shimmer_view_container.hideShimmer()
                shimmer_view_container.visibility = View.GONE
                lawyerMasterList = it
                adapter.setUpdateData(lawyerMasterList)
            }
        }
    }


    override fun onCellClickListener(lawyerDetails: LawyerSearchModelItem) {
        //startActivity(Intent(context, LawyaarDetailsActivity::class.java))
        val intent = Intent(context, LawyaarDetailsActivity::class.java)
        // intent.putExtra("userId" , lawyerDetails)
        startActivity(intent)
    }

    override fun onTalkClickListner(userId: String) {
        // initPayment(""+10)

        val intent = Intent(context, BookingSlotActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
        //  startActivity(Intent(activity, BookingSlotActivity::class.java))

    }

    override fun updateLawyaarDetails(postDataFilter: PostDataFilter) {
        lawyerSearchViewModel.lawyerSearchByFilter(
            tokenValue,
            "language,category,locations",
            postDataFilter
        )
        lawyerSearchViewModel.searchLawyerLiveData.observe(this) {
            if (it != null) {
                shimmer_view_container.hideShimmer()
                shimmer_view_container.visibility = View.GONE
                lawyerMasterList = it
                adapter.setUpdateData(lawyerMasterList)
            }
        }
    }

    override fun localSearch(searchText: CharSequence?) {
       adapter.localSearchData(searchText, lawyerMasterList)
    }
}