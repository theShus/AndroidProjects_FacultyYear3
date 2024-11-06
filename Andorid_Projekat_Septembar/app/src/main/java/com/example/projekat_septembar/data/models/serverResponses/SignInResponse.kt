package com.example.projekat_septembar.data.models.serverResponses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse(
    val userDetails: UserDetails,
    val session: String,
)
