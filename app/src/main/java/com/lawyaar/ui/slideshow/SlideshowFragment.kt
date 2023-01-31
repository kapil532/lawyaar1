package com.lawyaar.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.R
import com.lawyaar.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val veiw= inflater.inflate(R.layout.fragment_slideshow,container,false)

        return veiw
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}