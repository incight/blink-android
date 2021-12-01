package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseShopBrands(
    val data: BrandsData
) : Parcelable {}

@Parcelize
data class BrandsData(
    val products: Products
) : Parcelable {}

@Parcelize
data class Products(
    val aggregations: List<Aggregation>,
    val items: List<BItem>,
    val page_info: PageInfo
) : Parcelable {}


@Parcelize
data class Aggregation(
    val attribute_code: String,
    val count: Int,
    val label: String,
    val options: List<Option>
) : Parcelable {}


@Parcelize
data class BItem(
    val name: String,
    val price_range: BPriceRange,
    val sku: String
) : Parcelable {}


@Parcelize
data class PageInfo(
    val page_size: Int

) : Parcelable {}


@Parcelize
data class Option(
    val count: Int,
    val label: String,
    val value: String
) : Parcelable {}


@Parcelize
data class BPriceRange(
    val minimum_price: BMinimumPrice
) : Parcelable {}

@Parcelize
data class BMinimumPrice(
    val regular_price: BRegularPrice
) : Parcelable {}

@Parcelize
data class BRegularPrice(
    val currency: String,
    val value: Int
) : Parcelable {}

