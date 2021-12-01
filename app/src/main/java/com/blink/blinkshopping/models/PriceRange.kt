package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class PriceRange(
    var minimum_price:MinimumPrice
):Parcelable {}

@Parcelize
data class MinimumPrice(
    var discount: Discount,
    var regular_price: RegularPrice,
    var final_price: RegularPrice
):Parcelable {}

@Parcelize
data class Discount(
    var amount_off: Double,
    var percent_off: Double
):Parcelable {}

@Parcelize
data class RegularPrice(
    var value: Double,
    var currency: String
):Parcelable {}
