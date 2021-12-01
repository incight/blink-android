package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseMegaMenu(
    var data: MData?
) : Parcelable {}

@Parcelize
data class MData(
    var megaMenuLinks: MutableList<MegaMenuLink>
) : Parcelable {}

@Parcelize
data class MegaMenuLink(
    val title: String,
    val url: String
) : Parcelable {}

