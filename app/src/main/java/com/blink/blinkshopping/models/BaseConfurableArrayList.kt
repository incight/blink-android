package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Praveen Manepally on 12/12/2020.
 */
@Parcelize
data class BaseConfurableArrayList(
        var type: String,
        var basecolors: ConfurableArrayList
) : Parcelable {}