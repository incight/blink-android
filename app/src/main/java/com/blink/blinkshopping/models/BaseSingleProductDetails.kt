package com.blink.blinkshopping.models
import android.os.Parcelable
import android.text.Html
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseSingleProductDetails(
        val `data`: SData
) : Parcelable {}

@Parcelize
data class SData(
        val products: SProducts
) : Parcelable {}

@Parcelize
data class SProducts(
        val items: List<SItem>
) : Parcelable {}

@Parcelize
data class SItem(
        val __typename: String,
        val blink_highlighter: String,
        val blk_installment_enable: Int,
        val blk_installment_info: String?,
        val categories: List<SCategory>,
        val description: Description,
        val id: Int,
        val media_gallery: List<MediaGallery>,
        val media_gallery_entries: List<MediaGalleryEntry>,
        val meta_description: String,
        val name: String,
        val price: Price,
        val price_range: SPriceRange,
        val sku: String,
        val small_image: SmallImage,
        val stock_status: String,
        val url_key: String,

        val configurable_options: MutableList<ConfigurableOption>,
        val variants: MutableList<Variant>

) : Parcelable {
    fun html2text(): String? {
        var highlighter = ""
        if (blink_highlighter != null && blink_highlighter.isNotEmpty()) {
            highlighter = Html.fromHtml(Html.fromHtml(blink_highlighter).toString()).toString()
        } else {
            highlighter = ""
        }
        return highlighter
    }

    fun sku(): String {
        var sku1 = "SKU " + sku
        return sku1
    }


    fun media_gallery_URL(): String {
        var url = ""
        if (media_gallery != null && !media_gallery.isEmpty()) {
            url = media_gallery.get(0).url
        }
        return url
    }


    fun status(): String {
        var sku1 = "SKU " + stock_status
        if (stock_status.equals("IN_STOCK"))
            sku1 = "In Stock"
        else
            sku1 = "No Stock"
        return sku1
    }


    fun partSku(): String {
        var sku1 = "Part # " + sku
        return sku1
    }
}




@Parcelize
data class ConfigurableOption(
        val attribute_code: String,
        val attribute_id: String,
        val id: Int,
        val label: String,
        val values: MutableList<Value>
) : Parcelable {}


@Parcelize
data class Variant(
        val attributes: MutableList<Attribute>,
        val product: SProducts
) : Parcelable {}


@Parcelize
data class Value(
        val default_label: String,
        val label: String,
        val store_label: String,
        val swatch_data: SwatchData,
        val use_default_value: Boolean,
        val value_index: Int
) : Parcelable {}

@Parcelize
data class SCategory(
        val breadcrumbs: MutableList<Breadcrumb>,
        val id: Int
) : Parcelable {}


@Parcelize
data class SwatchData(
        val value: String
) : Parcelable {}

@Parcelize
data class Attribute(
        val code: String,
        val value_index: Int
) : Parcelable {}


@Parcelize
data class Breadcrumb(
        val category_id: Int
) : Parcelable {}

@Parcelize
data class Description(
        val html: String
) : Parcelable {
    fun html2text(): String? {
        var highlighter = ""
        if (html != null && html.isNotEmpty()) {
            highlighter = Html.fromHtml(Html.fromHtml(html).toString()).toString()
        } else {
            highlighter = ""
        }
        return highlighter
    }
}

@Parcelize
data class MediaGallery(
        val disabled: Boolean,
        val label: String,
        val url: String,
        val video_content: VideoContent?=null
) : Parcelable {}

@Parcelize
data class MediaGalleryEntry(
        val disabled: Boolean,
        val `file`: String,
        val id: Int,
        val label: String,
        val position: Int
) : Parcelable {}

@Parcelize
data class Price(
        val regularPrice: SRegularPrice
) : Parcelable {}

@Parcelize
data class SPriceRange(
        val maximum_price: MaximumPrice,
        val minimum_price: SMinimumPrice
) : Parcelable {}

@Parcelize
data class SmallImage(
        val url: String
) : Parcelable {}

@Parcelize
data class VideoContent(
        val media_type: String,
        val video_description: String,
        val video_metadata: String,
        val video_provider: String,
        val video_title: String,
        val video_url: String
) : Parcelable {}

@Parcelize
data class SRegularPrice(
        val amount: Amount
) : Parcelable {}

@Parcelize
data class Amount(
        val currency: String,
        val value: Double
) : Parcelable {}

@Parcelize
data class MaximumPrice(
        val discount: SDiscount,
        val final_price: FinalPrice,
        val regular_price: RegularPriceX
) : Parcelable {}

@Parcelize
data class SMinimumPrice(
        val discount: DiscountX,
        val final_price: FinalPriceX,
        val regular_price: RegularPriceXX
) : Parcelable {
    fun regularPrice(): String? {
        return "${regular_price.value} KD"
    }

    fun finalPrice(): String? {
        return "${final_price.value} KD"
    }

    fun checkPriceEquality(
    ): Boolean {
        return regular_price.value.equals(final_price.value)
    }
}

@Parcelize
data class SDiscount(
        val amount_off: Double,
        val percent_off: Double
) : Parcelable {}


@Parcelize
data class FinalPrice(
        val currency: String,
        val value: Double
) : Parcelable {}

@Parcelize
data class RegularPriceX(
        val currency: String,
        val value: Double
) : Parcelable {}

@Parcelize
data class DiscountX(
        val amount_off: Double,
        val percent_off: Double
) : Parcelable {}

@Parcelize
data class FinalPriceX(
        val currency: String,
        val value: Double
) : Parcelable {}

@Parcelize
data class RegularPriceXX(
        val currency: String,
        val value: Double
) : Parcelable {}



