package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseBrandImages(
    var `data`: BrandImgData
) : Parcelable {}

@Parcelize
data class BrandImgData(
    var brandsList: MutableList<Brands1>
) : Parcelable {}

@Parcelize

data class Brands1(
    var img: String,
    var label: String
) : Parcelable {}


