package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseProductsDetails(
    val data: ProData?
) : Parcelable {}

@Parcelize
data class ProData(
    val products: ProProducts
) : Parcelable {}

@Parcelize
data class ProProducts(
    val aggregations: MutableList<ProAggregation>,
    val items: MutableList<ProItem>,
    val page_info: ProPageInfo
) : Parcelable {}

@Parcelize
data class ProAggregation(
    val attribute_code: String,
    val count: Int,
    val label: String,
    val options: MutableList<ProOption>
) : Parcelable {}

@Parcelize
data class ProItem(
    val name: String,
    val price_range: ProPriceRange,
    val sku: String,
    val small_image: ProSmallImage,
    val stock_status: String
) : Parcelable {

    fun imageUrl(): String {
        var path = ""
        if (small_image.url != null) {
            if (small_image.url.contains("https://")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path + small_image.url
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


}

@Parcelize
data class ProPageInfo(
    val current_page: Int,
    val page_size: Int,
    val total_pages: Int
): Parcelable {}

@Parcelize
data class ProOption(
    val count: Int,
    val label: String,
    val value: String
) : Parcelable {}

@Parcelize
data class ProPriceRange(
    val minimum_price: ProMinimumPrice
) : Parcelable {}

@Parcelize
data class ProSmallImage(
    val url: String
) : Parcelable {}

@Parcelize
data class ProMinimumPrice(
    val discount: ProDiscount,
    val final_price: ProFinalPrice,
    val regular_price: ProRegularPrice
) : Parcelable {}

@Parcelize
data class ProDiscount(
    val amount_off: Double,
    val percent_off: Double
) : Parcelable {}

@Parcelize
data class ProFinalPrice(
    val currency: String,
    val value: Double
) : Parcelable {}

@Parcelize
data class ProRegularPrice(
    val currency: String,
    val value: Double
) : Parcelable {}