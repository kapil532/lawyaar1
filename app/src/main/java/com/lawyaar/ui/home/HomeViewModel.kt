package com.lawyaar.ui.home

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.retrofit.MainRepostry
import com.lawyaar.testlist.QuoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.security.auth.callback.Callback


//jab kabhi parameter waala veiw model banate hai
// tab humko veiwmodel factory bhi banana hota hai

class HomeViewModel constructor(private val repostry: MainRepostry): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repostry.getAllquotes()
        }



    }
        val quotes : LiveData<QuoteList>
        get() = repostry.quotes


}
