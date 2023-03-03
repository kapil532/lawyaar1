package com.lawyaar.models.wallets.wallet_post_pojo

data class WalletPostBody(
    val amount: Int,
    val amountRange: ArrayList<Int>,
    val dateRange: ArrayList<Long>,
    val point: Int,
    val userId: String
)