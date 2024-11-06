package com.example.projekat_septembar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val car: String,
    val car_model: String,
    val car_color: String,
    val car_model_year: Int,
    val car_vin: String,
    val price: String,
    val availability: Boolean,
)
