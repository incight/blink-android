package com.blink.blinkshopping.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 1/6/2021.
 */
@Parcelize
data class MainCategorySlider(
    var baseCategorySliders: BaseCategorySliders?,
    var subProductDetails: ProductDetails?,
    var subSubProductDetails: ProductDetails?
): Parcelable{}