package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseBestSeller(
    var data: BestData?
): Parcelable {}


@Parcelize
data class BestData(
    val bestSeller: MutableList<BestSeller>
): Parcelable {}

@Parcelize
data class BestSeller(
    val sku: String
): Parcelable {}