package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseOfferPlate(
    var data: OData?
): Parcelable {}


@Parcelize
data class OData(
    val offerplates: List<Offerplate>
) : Parcelable {}

@Parcelize
data class Offerplate(
    val description: String,
    val imageurl: String,
    val label: String,
    val link: String,
    val title: String
) : Parcelable {

    fun imageUrl(): String {
        var path = ""
        if (imageurl!=null){
            if (imageurl.contains("https://")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path + imageurl
    }

}