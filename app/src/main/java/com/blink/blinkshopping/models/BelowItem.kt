package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class BelowItem(
    var entity_id: Int,
    var sku: String,
    var name: String,
    var price: String,
    var imageurl: String
): Parcelable {}