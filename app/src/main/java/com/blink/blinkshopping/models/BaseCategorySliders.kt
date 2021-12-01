package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class BaseCategorySliders(
    var data: Data?,
    var sliderId:Int=-1,
    var position:Int=-1
): Parcelable {}