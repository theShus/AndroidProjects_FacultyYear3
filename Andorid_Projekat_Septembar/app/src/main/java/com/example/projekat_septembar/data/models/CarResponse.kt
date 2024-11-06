package com.example.projekat_septembar.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarResponse(
    var id: Long,
    var car: String,
    var car_model: String,
    var car_color: String,
    var car_model_year: Int,
    var car_vin: String,
    var price: String,
    var availability: Boolean,
)
