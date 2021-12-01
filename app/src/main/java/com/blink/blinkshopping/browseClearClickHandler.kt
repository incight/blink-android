package com.blink.blinkshopping

import com.apollographql.apollo.api.Input
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.type.AggregationsInput


interface browseClearClickHandler {
    fun browseClearClickHandler(skuClarList: ArrayList<Int>, itemListwise: HashMap<String, String>)
}
