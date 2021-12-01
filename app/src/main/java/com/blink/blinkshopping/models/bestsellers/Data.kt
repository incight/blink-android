package com.blink.blinkshopping.models.bestsellers


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bestSeller")
    val bestSeller: List<BestSeller>
)