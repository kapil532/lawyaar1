package com.lawyaar.models.wallet_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.wallets.veiw_model.WalletViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class AddWalletFactoryModel  @Inject constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddWalletViewModel(repostry) as T
    }
}