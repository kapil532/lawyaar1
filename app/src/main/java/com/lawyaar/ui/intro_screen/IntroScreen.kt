package com.lawyaar.ui.intro_screen

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.lawyaar.R
import com.lawyaar.adapters.SliderAdapter
import com.lawyaar.models.slider_model.SliderData
import com.lawyaar.ui.auth.OTPActivity
import com.lawyaar.ui.auth.PhoneActivity
import com.lawyaar.ui.base_screen.BaseActivity

class IntroScreen : BaseActivity() {
 
    // on below line we are creating a
    // variable for our view pager
    lateinit var viewPager: ViewPager
 
    // on below line we are creating a variable
    // for our slider adapter and slider list
    lateinit var sliderAdapter: SliderAdapter
    lateinit var sliderList: ArrayList<SliderData>
 
    // on below line we are creating a variable for our
    // skip button, slider indicator text view for 3 dots
    lateinit var skipBtn: Button
    lateinit var done_button: Button
    lateinit var skip_text: TextView
    lateinit var getstart_layout: RelativeLayout
    lateinit var next_layout: RelativeLayout
    lateinit var indicatorSlideOneTV: TextView
    lateinit var indicatorSlideTwoTV: TextView
    lateinit var indicatorSlideThreeTV: TextView
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slider_screen)
 
        // on below line we are initializing all
        // our variables with their ids.
        viewPager = findViewById(R.id.idViewPager)
        skipBtn = findViewById(R.id.idBtnSkip)
        done_button = findViewById(R.id.done_button)
        skip_text = findViewById(R.id.skip_text)
        next_layout = findViewById(R.id.next_layout)
        getstart_layout = findViewById(R.id.getstart_layout)
        indicatorSlideOneTV = findViewById(R.id.idTVSlideOne)
        indicatorSlideTwoTV = findViewById(R.id.idTVSlideTwo)
        indicatorSlideThreeTV = findViewById(R.id.idTVSlideThree)


        skipBtn.setOnClickListener({
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
        })
        // on below line we are adding click listener for our skip button
        skip_text.setOnClickListener {
            // on below line we are opening a new activity
            val i = Intent(this@IntroScreen, PhoneActivity::class.java)
            startActivity(i)
        }
  done_button.setOnClickListener {
            // on below line we are opening a new activity
            val i = Intent(this@IntroScreen, PhoneActivity::class.java)
            startActivity(i)
        }

        // on below line we are initializing our slider list.
        sliderList = ArrayList()
 
        // on below line we are adding data to our list
        sliderList.add(
            SliderData(
                "",
                "Get legal help on-the-go with our lawyaar app!",
                R.drawable.intro_one
            )
        )
 
        sliderList.add(
            SliderData(
                "",
                "No more stressing over legal issues! We've got you covered.",
                R.drawable.intro_two
            )
        )
 
        sliderList.add(
            SliderData(
                "",
                "Connect with our lawyaar, who will fight for your rights!",
                R.drawable.intro_three
            )
        )
 
        // on below line we are adding slider list
        // to our adapter class.
        sliderAdapter = SliderAdapter(this, sliderList)
 
        // on below line we are setting adapter
        // for our view pager on below line.
        viewPager.adapter = sliderAdapter
 
        // on below line we are adding page change
        // listener for our view pager.
        viewPager.addOnPageChangeListener(viewListener)
 
    }
 
    // creating a method for view pager for on page change listener.
    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }
 
        override fun onPageSelected(position: Int) {
            // we are calling our dots method to
            // change the position of selected dots.
 
            // on below line we are checking position and updating text view text color.
            if (position == 0) {
                indicatorSlideTwoTV.setTextColor(resources.getColor(R.color.grey))
                indicatorSlideThreeTV.setTextColor(resources.getColor(R.color.grey))
                indicatorSlideOneTV.setTextColor(resources.getColor(R.color.blue))
                next_layout.visibility = View.VISIBLE
                getstart_layout.visibility = View.GONE

            } else if (position == 1) {
                indicatorSlideTwoTV.setTextColor(resources.getColor(R.color.blue))
                indicatorSlideThreeTV.setTextColor(resources.getColor(R.color.grey))
                indicatorSlideOneTV.setTextColor(resources.getColor(R.color.grey))
                next_layout.visibility = View.VISIBLE
                getstart_layout.visibility = View.GONE
            } else {
                indicatorSlideTwoTV.setTextColor(resources.getColor(R.color.grey))
                indicatorSlideThreeTV.setTextColor(resources.getColor(R.color.blue))
                indicatorSlideOneTV.setTextColor(resources.getColor(R.color.grey))
                next_layout.visibility = View.GONE
                getstart_layout.visibility = View.VISIBLE
            }
        }
 
        // below method is use to check scroll state.
        override fun onPageScrollStateChanged(state: Int) {

        }
    }
}