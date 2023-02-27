package com.lawyaar.models.wallets.veiw_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.location.LocationModel
import com.lawyaar.models.wallets.WalletModel
import com.lawyaar.models.wallets.wallet_post_pojo.WalletPostBody
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WalletViewModel constructor(private val repostry: MainRepostry) : ViewModel()
{


     fun getWalletsList(token: String,userId: String,walletPostBody: WalletPostBody)
     {
         viewModelScope.launch(Dispatchers.IO) {
             repostry.getWalletsDetails_R(token,userId,walletPostBody)
         }
     }

    val wallet: LiveData<WalletModel>
        get() = repostry.walletLive


}