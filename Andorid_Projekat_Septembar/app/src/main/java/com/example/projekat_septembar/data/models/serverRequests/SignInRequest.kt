package com.example.projekat_septembar.data.models.serverRequests

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInRequest(
    val userName: String,
    val password: String,
    val verified: Boolean
    )
