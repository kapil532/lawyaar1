package com.lawyaar.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lawyaar.MainActivity
import com.lawyaar.R
import com.lawyaar.adapters.LawyaarAdapter
import com.lawyaar.retrofit.LawyaarApi
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.CellClickListener

class HomeFragment : Fragment(),CellClickListener {

    private  var layoutManager : RecyclerView.LayoutManager? =null
    private lateinit var adapter : LawyaarAdapter
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val lawyaarApi = RetrofitHelperObj.getInstance().create(LawyaarApi::class.java)
        val repostry = MainRepostry(lawyaarApi)
        homeViewModel  = ViewModelProvider(this,HomeViewModelFactory(repostry)).get(HomeViewModel::class.java)
        //var data = ArrayList<com.lawyaar.testlist.Result>
        val veiw= inflater.inflate(R.layout.fragment_home,container,false)

        var recycle_veiw = veiw.findViewById<RecyclerView>(R.id.recycle_veiw)

        recycle_veiw.layoutManager = LinearLayoutManager(activity)
        adapter = LawyaarAdapter()
        recycle_veiw.adapter = adapter



        homeViewModel.quotes.observe(this, Observer<QuoteList> {
            if (it != null)
            {
                adapter.setUpdateData(it.results)
            }
        })

        adapter.setUplistner(this)

//        adapter.onI

        return veiw
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCellClickListener()
    {
        startActivity(Intent(context , LawyaarDetailsActivity::class.java))
    }
}