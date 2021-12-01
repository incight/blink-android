package com.blink.blinkshopping.models.allcategorymodel

data class Children(
    val children: MutableList<ChildrenX>,
    val children_count: String,
    val id: Int,
    val image: String,
    val include_in_menu: Int,
    val level: Int,
    val name: String,
    val path: String,
    val position: Int,
    val url_path: String,
    val description: String

)