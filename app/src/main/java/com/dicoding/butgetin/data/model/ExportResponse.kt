package com.dicoding.butgetin.data.model

data class ExportResponse(
    val success: Boolean,
    val message: String,
    val fileUrl: String
)
