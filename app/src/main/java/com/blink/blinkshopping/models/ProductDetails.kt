package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class ProductDetails(var items: MutableList<ProductItem>):Parcelable {
}