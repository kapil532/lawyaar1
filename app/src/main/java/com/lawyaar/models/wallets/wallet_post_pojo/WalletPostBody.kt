package com.lawyaar.models.wallets.wallet_post_pojo

data class WalletPostBody(
    val amount: Int,
    val amountRange: List<Int>,
    val dateRange: List<Long>,
    val point: Int,
    val userId: String
)