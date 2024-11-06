package com.example.projekat_septembar.data.models.serverResponses

import com.example.projekat_septembar.data.models.CarResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSearchCarsResponse(
    val Cars: List<CarResponse>
)
