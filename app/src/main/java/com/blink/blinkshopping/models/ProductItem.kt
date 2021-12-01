package com.blink.blinkshopping.models

import android.graphics.Paint
import android.os.Parcelable
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize


/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class ProductItem(
    var id: String,
    var name: String,
    var sku: String,
    var image: Image,
    var small_image: Image,
    var price_range: PriceRange,

    val stock_status: String

) : Parcelable {

    fun imageUrl(): String {
        var path = ""
        if (image.url != null) {
            if (image.url.contains("https://")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path + image.url
    }

    fun actualPrice_(): String {
        val dodActuvalPrice = price_range.minimum_price.regular_price.value.toString() + " KD"
        return dodActuvalPrice
    }

    fun actualPrice(): String {
        val dodActuvalPrice = price_range.minimum_price.regular_price.value.toString() + "KD"
        return dodActuvalPrice
    }

    fun offeredPrice(): String {
        val dodOfferedPrice = price_range.minimum_price.final_price.value.toString() + " KD"
        return dodOfferedPrice
    }

    fun checkPriceEquality(
    ): Boolean {
        return price_range.minimum_price.regular_price.value.toString()
            .equals(price_range.minimum_price.final_price.value.toString())
    }

    fun getFormatlistPrice(price: String): SpannableStringBuilder {
        val spannableString1 = SpannableStringBuilder(price)
        spannableString1.setSpan(StrikethroughSpan(), 0, price.length, 0)
        return spannableString1
    }

}



