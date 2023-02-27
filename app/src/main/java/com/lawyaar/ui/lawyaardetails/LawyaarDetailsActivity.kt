package com.lawyaar.ui.lawyaardetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.R
import com.lawyaar.application.LawyaarApplication
import com.lawyaar.databinding.ActivityMainBinding
import com.lawyaar.models.lawyer_detail.LawyerModel
import com.lawyaar.models.lawyer_detail.view_model_factory.LawyerDetailsFactoryModel
import com.lawyaar.models.lawyer_detail.view_model_factory.LawyerDetailsViewModel
import com.lawyaar.models.lawyer_search.post_details.LawyerSearchModelItem
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.models.location.view_factory_model.LocationViewModelFactory
import com.lawyaar.preference.ModelPreferencesManager
import com.lawyaar.ui.base_screen.BaseActivity
import com.lawyaar.ui.book_slots.BookingSlotActivity
import javax.inject.Inject

class LawyaarDetailsActivity : BaseActivity()
{


    lateinit var lawyerDetailsViewModel: LawyerDetailsViewModel

    @Inject
    lateinit var lawyerDetailsFactoryModel: LawyerDetailsFactoryModel

   //val lawyerSearchModelItem
   lateinit var lawyerSearchModelItem : LawyerSearchModelItem
    //lateinit var binding: ActivityMainBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  binding = ActivityMainBinding.inflate(layoutInflater)
        // val view = binding.root
        setContentView(R.layout.lawyer_profile_layout)
        lawyerSearchModelItem = ModelPreferencesManager.get<LawyerSearchModelItem>("LAWYAR_DETAILS")
        // to bazsck the past activity
        val back_icon = findViewById<ImageView>(R.id.back_icon)
        back_icon.setOnClickListener({
            finish()
        })

        val appoint_button = findViewById<Button>(R.id.appoint_button)
        appoint_button.setOnClickListener({
            startActivity(Intent(this, BookingSlotActivity::class.java))
        })

        initNetwork()
        // initializeView()

    }

    fun initializeView(it: LawyerModel) {
        val rating_no = findViewById<TextView>(R.id.rating_no)
        val lawyer_type = findViewById<TextView>(R.id.lawyer_type)
        val lawyaar_name = findViewById<TextView>(R.id.lawyaar_name)
        val lawyaar_exper = findViewById<TextView>(R.id.lawyaar_exper)
        val experience = findViewById<TextView>(R.id.experience)
        val lawyer_reveiw = findViewById<TextView>(R.id.lawyer_reveiw)
        val about_lawyer = findViewById<TextView>(R.id.about_lawyer)
        val cases_count = findViewById<TextView>(R.id.cases_count)
        val price_hour = findViewById<TextView>(R.id.price_hour)
        val reveiw_count = findViewById<TextView>(R.id.reveiw_count)


        var  caseS =""
        var  langS =""
        if(lawyerSearchModelItem.caseCategories.size  >0) {

            for ( case in lawyerSearchModelItem.caseCategories )
            {
                caseS += " "+case.name
            }
            for ( lang in lawyerSearchModelItem.languages )
            {
                langS += " "+lang.name
            }
        }
        about_lawyer.setText(langS)
        lawyaar_exper.setText(caseS)
        lawyer_type.setText(caseS)
        lawyaar_name.setText(lawyerSearchModelItem.name)
        experience.setText(it.experience)
        price_hour.setText(it.offerPrice)
    }

    var tokenValue = ""
    var user_id = ""
    fun initNetwork() {
        val sharedPreferences: SharedPreferences =
            application!!.getSharedPreferences("token_auth", Context.MODE_PRIVATE)


        tokenValue = sharedPreferences.getString("token_val", " ").toString()
        user_id = sharedPreferences.getString("user_id", " ").toString()


        (application as LawyaarApplication).applicationComponent.inject(this)

        lawyerDetailsViewModel =
            ViewModelProvider(
                this,
                lawyerDetailsFactoryModel
            ).get(LawyerDetailsViewModel::class.java)
        Log.d("", "--> NUL VALUE"+lawyerSearchModelItem.userId)
        lawyerDetailsViewModel.getLawyerDetails(tokenValue, "language,category,locations", lawyerSearchModelItem.advocateDetail.advocateDetailId)
        lawyerDetailsViewModel.getLawyerLiveData.observe(this, Observer<LawyerModel> {
            if (it != null) {

                Log.d("", "--> NUL VALUE"+it.experience)
                initializeView(it)
            } else {
                Log.d("", "--> NUL VALUE")
            }
        })

    }


}