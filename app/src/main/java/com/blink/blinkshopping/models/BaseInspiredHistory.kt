package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseInspiredHistory(
        var data: IData?
): Parcelable {}

@Parcelize
data class IData(
        val newArrivalsproducts: MutableList<InspiredHistoryproduct>
):Parcelable {}

@Parcelize
data class InspiredHistoryproduct(
        val imageurl: String,
        val name: String,
        val price: String,
        val sku: String
):Parcelable {}

