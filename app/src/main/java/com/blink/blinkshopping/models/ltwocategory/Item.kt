package com.blink.blinkshopping.models.ltwocategory


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("block_type")
    val blockType: String,
    @SerializedName("block_type_id")
    val blockTypeId: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("position")
    val position: Int,
    @SerializedName("status")
    val status: Int
)