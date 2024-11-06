package com.example.projekat_septembar.data.models.serverResponses

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class SellerDetailsResponse(
    val id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val gender: String,
    val birthdate: String,
    val company_name: String,
    val department: String,
    val job_title: String,
    val address: Any,
    val phone: String,
    val avatar: String,
    val email_verified: Boolean,
    val password: String,
    val last_login: String,
    val last_login_ip: String,
    val subscribed: Boolean,
)
