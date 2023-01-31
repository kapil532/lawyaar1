package com.lawyaar.ui.home

import android.content.Intent
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
import com.lawyaar.MainActivity
import com.lawyaar.R
import com.lawyaar.adapters.LawyaarAdapter
import com.lawyaar.retrofit.LawyaarApi
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.testlist.QuoteList
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.utils.CellClickListener
import com.lawyaar.utils.TalkListner
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment(),CellClickListener ,TalkListner,PaymentResultListener {

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

        adapter.setUplistner(this,this)

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

    override fun onTalkClickListner() {
        initPayment(""+10)

    }

    fun initPayment(amount : String)
    {
        val amt =amount
        // rounding off the amount.
        val amount = Math.round(amt.toFloat() * 100).toInt()
        val checkout = Checkout()
        // on the below line we have to see our id.
        checkout.setKeyID("rzp_test_GJV9Uoi7YR7m2S")
        // set image
        checkout.setImage(R.drawable.lawyaar_icon)
        val obj = JSONObject()
        try {
            obj.put("name", "Lawyaar.co")
            obj.put("description", "Test payment")
            obj.put("theme.color", "")
            obj.put("currency", "INR")
            obj.put("amount", amount)
            obj.put("prefill.contact", "8095128426")
            obj.put("prefill.email", "lawyaar@lawyaar.co")
            checkout.open(activity, obj)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?)
    {
        Toast.makeText(context, ""+p0, Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?)
    {
    }
}