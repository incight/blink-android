package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseBanners(
    var data: BData?
): Parcelable {}

@Parcelize
data class BData(
    val bannerId: MutableList<BannerId>
): Parcelable {}

@Parcelize
data class BannerId(
    val banner_id: Int,
    val content: String,
    val image: String,
    val name: String,
    val newtab: Int,
    val status: Int,
    val title: String,
    val type: Int,
    val url_banner: String
): Parcelable {
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
