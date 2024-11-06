package com.example.projekat_septembar.data.models.serverRequests

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val mobile: Long,
    val country: String,
)
