package com.example.projekat_septembar.data.models.serverResponses

import com.example.projekat_septembar.data.models.CarResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCarsResponse(
    val cars: List<CarResponse>
)
