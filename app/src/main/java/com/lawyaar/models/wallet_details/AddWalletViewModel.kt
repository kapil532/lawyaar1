package com.lawyaar.models.wallet_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.wallets.WalletModel
import com.lawyaar.models.wallets.wallet_post_pojo.WalletPostBody
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWalletViewModel  constructor(private val repostry: MainRepostry) : ViewModel()
{


    fun addWalletPoints(token: String,userId: String,points :String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repostry.addWalletPoints(token,userId,points)
        }
    }

    val addWalletLD: LiveData<AddWalletModel>
        get() = repostry.addWalletPointsLD


}