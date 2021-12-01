package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 1/6/2021.
 */
@Parcelize
data class MainProductDetails (var index: Int,var baseProductDetails: BaseProductDetails): Parcelable {
}