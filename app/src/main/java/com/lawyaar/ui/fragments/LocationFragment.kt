package com.lawyaar.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.R
import com.lawyaar.adapters.LocationAdaptor
import com.lawyaar.retrofit.LawyaarApi
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.home.HomeViewModel
import com.lawyaar.ui.home.HomeViewModelFactory

class LocationFragment : Fragment() {
    lateinit var locationModel: LocationModel
    lateinit var locationAdaptor: LocationAdaptor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val veiw = inflater.inflate(R.layout.location_fragment_layout, container, false)
        var recycle_veiw = veiw.findViewById<RecyclerView>(R.id.location_recycle_veiw)
        recycle_veiw.layoutManager = GridLayoutManager(context, 5)
        locationAdaptor = LocationAdaptor()
        initNetwork()
        return veiw
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("FragmentLiveDataObserve")
    fun initNetwork() {
        val lawyaarApi = RetrofitHelperObj.getInstance().create(LawyaarApi::class.java)
        val repostry = MainRepostry(lawyaarApi)
        locationModel = ViewModelProvider(
            this,
            LocationModelFactory(repostry)
        ).get(LocationModel::class.java)
        locationModel.quotes.observe(this, Observer<QuoteList> {
            if (it != null) {
                locationAdaptor.setUpdateData(it.results)
            }
        })
    }
}