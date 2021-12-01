package com.blink.blinkshopping.models.gridcategory

data class Children(
    val children_count: String,
    val id: Int,
    val image: String,
    val include_in_menu: Int,
    val level: Int,
    val name: String,
    val path: String,
    val position: Int,
    val url_path: String
)