package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/13/2020.
 */
@Parcelize
data class BaseCategoryArrayList(
    var type: String,
    var baseSlider: BaseSlider,
    var baseSubCategory: BaseSubCategory,
    var baseNewArrivals: BaseNewArrivals,
    var baseDynamicLinks: BaseDynamicLinks,
    var baseCategorySliders: BaseCategorySliders
): Parcelable {}