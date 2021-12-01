package com.blink.blinkshopping

import com.apollographql.apollo.api.Input
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.type.AggregationsInput


interface ItemClickHandler {
    fun onItemClick(position: String, item: ProductItem?, from: String)
}
