package com.blink.blinkshopping.models.gridcategory

data class Category(
    val children: List<Children>,
    val children_count: String,
    val id: Int,
    val image: Any,
    val include_in_menu: Int,
    val level: Int,
    val name: String,
    val path: String,
    val position: Int,
    val url_path: Any
)