package com.dicoding.butgetin.api

data class Transaction(
    val date: String,
    val category: String,
    val amount: Double,
    val isExpense: Boolean
)