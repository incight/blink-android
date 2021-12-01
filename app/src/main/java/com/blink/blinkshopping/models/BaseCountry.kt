package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseCountry(
    val data: countrycode
): Parcelable {}

@Parcelize
data class countrycode(
    val countrycode: MutableList<Countries>
): Parcelable {}

@Parcelize
data class Countries(
    val __typename : String,
    val country_code: String,
    val mobile_code: String,
    val lable: String,
    val flag: String,
    val id: Int
): Parcelable {}