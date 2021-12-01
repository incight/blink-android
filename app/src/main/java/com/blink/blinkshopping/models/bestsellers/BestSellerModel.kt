package com.blink.blinkshopping.models.bestsellers


import com.google.gson.annotations.SerializedName

data class BestSellerModel(
    @SerializedName("data")
    val `data`: Data?
)