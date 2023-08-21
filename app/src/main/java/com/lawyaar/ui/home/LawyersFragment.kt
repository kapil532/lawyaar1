package com.lawyaar.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.HomeScreenActivity
import com.lawyaar.R
import com.lawyaar.adapters.LawyerAdapter
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.core.constants.FILTER_TYPES
import com.lawyaar.core.constants.SHARED_PREF_AUTH_TOKEN
import com.lawyaar.core.constants.SHARED_PREF_AUTH_TOKEN_VALUE
import com.lawyaar.models.lawyer_search.post_data.PostDataFilter
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchFactoyModel
import com.lawyaar.models.lawyer_search.view_model.LawyerSearchViewModel
import com.lawyaar.utils.FilterOption
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
class LawyersFragment : Fragment(), FilterOption {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var lawyerAdapter: LawyerAdapter
    lateinit var recycleView: RecyclerView
    lateinit var lawyerSearchViewModel: LawyerSearchViewModel

    @Inject
    lateinit var lawyerSearchFactoyModel: LawyerSearchFactoyModel

    private var lawyerMasterList = ArrayList<LawyerSearchModelItem>()
    var tokenValue = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lawyers_fragment, container, false)

        (activity?.application as LawyaarApplication).applicationComponent.inject(lawyersFragment = this)
        lawyerSearchViewModel =
            ViewModelProvider(this, lawyerSearchFactoyModel)[LawyerSearchViewModel::class.java]
        HomeScreenActivity.filterOption = this
        lawyerAdapterInit(view)
        lawyerObserver()
        return view
    }

    private fun lawyerAdapterInit(view: View) {
        recycleView = view.findViewById(R.id.lawyer_recycler_view)
        recycleView.layoutManager = LinearLayoutManager(activity)
        lawyerAdapter = LawyerAdapter()
        recycleView.adapter = lawyerAdapter
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun lawyerObserver() {
        val sharedPreferences: SharedPreferences =
            activity?.application!!.getSharedPreferences(SHARED_PREF_AUTH_TOKEN, Context.MODE_PRIVATE)
        tokenValue = sharedPreferences.getString(SHARED_PREF_AUTH_TOKEN_VALUE, " ").toString()
        lawyerSearchViewModel.lawyerSearchByFilter(
            tokenValue,
            FILTER_TYPES,
            clearFilterForAllData()
        )
        lawyerSearchViewModel.searchLawyerLiveData.observe(this) {
            if (it != null) {
                lawyerMasterList = it
                lawyerAdapter.setUpdateData(lawyerMasterList)
            }
        }
    }

    private fun clearFilterForAllData(): PostDataFilter {
        val caseCategories: ArrayList<String> = ArrayList()
        val locations: ArrayList<String> = ArrayList()
        val lawyerCategories: ArrayList<String> = ArrayList()
        val offerPriceRange: ArrayList<Int> = ArrayList()
        val actualPriceRange: ArrayList<Int> = ArrayList()
        val languages: ArrayList<String> = ArrayList()
        caseCategories.clear()
        locations.clear()
        languages.clear()
        lawyerCategories.clear()
        offerPriceRange.clear()
        actualPriceRange.clear()
        return PostDataFilter(
            actualPriceRange,
            caseCategories,
            languages,
            lawyerCategories,
            locations,
            offerPriceRange
        )
    }

    override fun updateLawyaarDetails(postDataFilter: PostDataFilter) {
        lawyerSearchViewModel.lawyerSearchByFilter(
            tokenValue,
            FILTER_TYPES,
            postDataFilter
        )
        lawyerSearchViewModel.searchLawyerLiveData.observe(this) {
            if (it != null) {
                lawyerMasterList = it
                lawyerAdapter.setUpdateData(lawyerMasterList)
            }
        }
    }

    override fun localSearch(searchText: CharSequence?) {
        lawyerAdapter.localSearchData(searchText, lawyerMasterList)
    }
}