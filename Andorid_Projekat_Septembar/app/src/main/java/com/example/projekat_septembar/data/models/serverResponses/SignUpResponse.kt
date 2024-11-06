package com.example.projekat_septembar.data.models.serverResponses

import com.example.projekat_septembar.data.models.serverRequests.SignUpRequest
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
    val Data: SignUpRequest,
    val Message: String,
    val UserId: String,
    val CreatedAt: Long,
    val NextSteps: String,
    val EmailValidation: String,
    val Subscribed: Boolean,

)
