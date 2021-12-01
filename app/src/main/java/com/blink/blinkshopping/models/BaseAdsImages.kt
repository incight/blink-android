package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseAdsImages(
    val data: AdsImgData?
): Parcelable {}

@Parcelize
data class AdsImgData(
    val adsimages: MutableList<Adsimage>
): Parcelable {}

@Parcelize
data class Adsimage(
    val items: MutableList<AdsItems>,
    val title: String
): Parcelable {}
