package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Praveen Manepally on 12/12/2020.
 */
@Parcelize
data class ConfurableArrayList(
        var typeName: String,
        val confurableValues: ConfurableValues
) : Parcelable {}

@Parcelize
data class ConfurableValues(
        val values: MutableList<String>,
        val value_index: MutableList<String>,
        val swatchValue: MutableList<String>?,
        val from: String
        ) : Parcelable {}