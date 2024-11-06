package com.example.projekat_septembar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val password: String,
    val name: String,
    val lastname: String,
    val phone: Long,
    val country: String

)