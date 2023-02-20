package com.lawyaar.models.wallets

data class WalletModelItem(
    val amount: Int,
    val createdDate: Long,
    val point: Int,
    val userId: String,
    val walletId: String
)