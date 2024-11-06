package com.example.projekat_septembar.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SellerDetails(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val avatar: String,
): Parcelable
