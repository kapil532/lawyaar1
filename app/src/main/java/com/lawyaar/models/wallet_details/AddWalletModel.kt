package com.lawyaar.models.wallet_details

data class AddWalletModel(
    val amount: Int,
    val createdDate: Long,
    val point: Int,
    val userId: String,
    val walletId: String
)