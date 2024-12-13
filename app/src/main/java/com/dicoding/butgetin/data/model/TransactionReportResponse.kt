package com.dicoding.butgetin.data.model

import android.view.SurfaceControl

data class TransactionReportResponse(
    val id: String,
    val transactions: List<SurfaceControl.Transaction>
)
