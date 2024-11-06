package com.example.projekat_septembar.data.models.serverResponses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetails(
    val userName: String,
    val password: String,
    val verified: Boolean,
)
