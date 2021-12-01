package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseStorePickup(
    val `data`: StoreData
) : Parcelable {}


@Parcelize
data class StoreData(
    val getSourceStores: List<GetSourceStore>
) : Parcelable {}


@Parcelize
data class GetSourceStore(
    val address: String,
    val pickipfrom: String,
    val source_code: String
) : Parcelable {

    fun clearSpace(sourceCode: String): String {
        return sourceCode.trim()
    }

}