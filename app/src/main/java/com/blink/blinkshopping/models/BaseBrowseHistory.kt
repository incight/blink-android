package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseBrowseHistory(
    val data: HistoryData?
) : Parcelable {}

@Parcelize
data class HistoryData(
    val browsingHistory: MutableList<BrowsingHistory>
) : Parcelable {}

@Parcelize
data class BrowsingHistory(
    val entity_id: Int,
    val sku: String
) : Parcelable {}


