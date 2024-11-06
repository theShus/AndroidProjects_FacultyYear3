package com.example.projekat_septembar.data.models.serverRequests

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactSellerRequest(
    val firstName: String,
    val lastName: String,
    val message: String,
    val contact: Int
)
