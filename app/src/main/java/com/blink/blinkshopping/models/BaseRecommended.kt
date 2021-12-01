package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseRecommended(
        var data: RData?
): Parcelable {}

@Parcelize
data class RData(
        val newArrivalsproducts: MutableList<Recommendedproduct>
):Parcelable {}

@Parcelize
data class Recommendedproduct(
        val imageurl: String,
        val name: String,
        val price: String,
        val sku: String
):Parcelable {}

