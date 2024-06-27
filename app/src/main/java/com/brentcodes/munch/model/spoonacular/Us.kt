package com.brentcodes.munch.model.spoonacular


import com.google.gson.annotations.SerializedName

data class Us(
    @SerializedName("unit")
    val unit: String,
    @SerializedName("value")
    val value: Double
)