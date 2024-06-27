package com.brentcodes.munch.model.spoonacular


import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("metric")
    val metric: Metric,
    @SerializedName("us")
    val us: Us
)