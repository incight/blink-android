package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/13/2020.
 */
@Parcelize
data class BaseSlider(
    var data: SlidersData?
) : Parcelable {}

@Parcelize
data class SlidersData(var sliderById: MutableList<SlidersById>) : Parcelable {}

@Parcelize
data class SlidersById(var slider_id: Int, var items: MutableList<Items>) : Parcelable {}

@Parcelize
data class Items(var banner_id: Int, var name: String, var title: String, var image: String) :
    Parcelable {}
