package com.blink.blinkshopping.models.shopbybrand

data class Aggregation(
    val attribute_code: String,
    val count: Int,
    val label: String,
    val options: List<Option>
)