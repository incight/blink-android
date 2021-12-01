package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/13/2020.
 */
@Parcelize
data class BaseDailyDealsproducts(
    var data: DailyDealsProductsData?
):Parcelable {}


@Parcelize
data class DailyDealsProductsData(
    var dailyDealsproducts: MutableList<DailyDealsproducts>
):Parcelable {}


@Parcelize
data class DailyDealsproducts(
    var name: String,
    var sku: String,
    var price: String,
    var specialprice: String,
    var dealfrom: String,
    var dealto: String
):Parcelable {}