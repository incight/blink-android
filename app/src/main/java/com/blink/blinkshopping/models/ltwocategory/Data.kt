package com.blink.blinkshopping.models.ltwocategory

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("layouts")
    val layouts: List<Layout>
)