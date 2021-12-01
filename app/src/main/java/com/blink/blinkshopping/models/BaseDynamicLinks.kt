package com.blink.blinkshopping.models

import android.os.Parcelable
import android.text.Html
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseDynamicLinks(
    var data: DData?
) : Parcelable {}

@Parcelize
data class DData(
    val dynamicBlocks: MutableList<DynamicBlock>
) : Parcelable {}

@Parcelize
data class DynamicBlock(
    val items: List<Item>,
    val title: String,
    val image: String
) : Parcelable {
    fun imageUrl(): String {
        var path = ""
        if (image != null) {
            if (image.contains("https://")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path + image
    }


    fun html2text(): String? {
        var highlighter = ""
        if (title != null && title.isNotEmpty()) {
            highlighter = Html.fromHtml(Html.fromHtml(title).toString()).toString()
        } else {
            highlighter = ""
        }
        return highlighter
    }

    
}

/*

@Parcelize
data class Item(
        val name: String,
        val price: String,
        val sku: String
): Parcelable {}
*/
