package com.lawyaar.models.wallets.veiw_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lawyaar.models.location.view_factory_model.LocationViewModel
import com.lawyaar.retrofit.MainRepostry
import javax.inject.Inject

class WalletModelFactory @Inject constructor(private val repostry: MainRepostry) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WalletViewModel(repostry) as T
    }
}