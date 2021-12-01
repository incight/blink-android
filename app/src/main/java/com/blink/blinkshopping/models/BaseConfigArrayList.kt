package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Praveen Manepally on 12/12/2020.
 */
@Parcelize
data class BaseConfigArrayList(
        var type: String,
        val configurable_options: MutableList<ConfigurableOption>
) : Parcelable {}