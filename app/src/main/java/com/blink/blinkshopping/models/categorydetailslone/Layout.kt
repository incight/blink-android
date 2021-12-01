package com.blink.blinkshopping.models.categorydetailslone

data class Layout(
    val id: Int,
    val items: List<Item>,
    val page: String,
    val status: Int
)