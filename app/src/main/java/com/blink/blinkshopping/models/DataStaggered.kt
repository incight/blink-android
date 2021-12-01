package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 10/29/2020.
 */
@Parcelize
data class DataStaggered(
    var image: String
) : Parcelable {
    fun imageUrl(): String {
        var path = ""
        if (image != null) {
            if (image.contains("https://")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path + image
    }
}