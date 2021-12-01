package com.blink.blinkshopping.models.ltwocategory


import com.google.gson.annotations.SerializedName

data class Layout(
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("page")
    val page: String,
    @SerializedName("status")
    val status: Int
)