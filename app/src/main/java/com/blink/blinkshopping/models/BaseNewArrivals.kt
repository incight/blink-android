package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseNewArrivals(
        var data: NData?
): Parcelable {}

@Parcelize
data class NData(
        val newArrivalsproducts: MutableList<NewArrivalsproduct>
):Parcelable {}

@Parcelize
data class NewArrivalsproduct(
        val imageurl: String,
        val name: String,
        val price: String,
        val sku: String
):Parcelable {}

