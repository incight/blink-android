package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/13/2020.
 */
@Parcelize
data class BaseAdsBlocks(
    var data: AdsData?
):Parcelable {}

@Parcelize
data class AdsData(
    var adsBlocks: MutableList<AdsBlocks>
):Parcelable {}

@Parcelize
data class AdsBlocks(
    var items: MutableList<AdsItems>
):Parcelable {}

@Parcelize
data class AdsItems(
    var image: String,
    var title: String,
    var url: String,
    val status: Int,
    val ads_block_id: String,
    val ads_block_image_id: String,
    val name: String

):Parcelable {

    fun imageUrl(): String {
        var path = ""
        if (image!=null){
            if (image.contains("https://")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path + image
    }
}

