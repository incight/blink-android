package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseHomeModel(
    var data: HData?
) : Parcelable {}

@Parcelize
data class HData(
    val dynamicBlocks: MutableList<Layout>
) : Parcelable {}


@Parcelize
data class Layout(
    val id: Int,
    val items: MutableList<HItem>,
    val page: String,
    val status: Int
) : Parcelable {}

@Parcelize
data class HItem(
    val block_type: String,
    val block_type_id: String,
    val id: Int,
    val position: Int,
    val status: Int
) : Parcelable {}
