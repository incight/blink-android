package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class CategorySliders(
    var id: Int,
    var title: String,
    var category_image: String,
    var status: Int,
    var block_position: String,
    var sort_order: Int,
    var right_slider: Int,
    var bottom_slider: Int,
    var which_category: String,
    var hero_type: String,
    var fixed_type:String,
    var select_category: String,
    var hero_sku: String,
    var image: String,
    var image_url: String,
    var video: String,
    var items: MutableList<Item>,
    var belowitems: MutableList<BelowItem>
): Parcelable {

    fun isImage():Boolean{
        return fixed_type.equals("image")
    }

    fun isHeroSku():Boolean{
        return fixed_type.equals("hero_sku")
    }

    fun isVideo():Boolean{
        return fixed_type.equals("video")
    }

}