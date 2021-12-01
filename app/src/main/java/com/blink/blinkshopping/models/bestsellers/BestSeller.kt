package com.blink.blinkshopping.models.bestsellers


import com.google.gson.annotations.SerializedName

data class BestSeller(
    @SerializedName("sku")
    val sku: String
)