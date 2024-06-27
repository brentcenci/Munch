package com.brentcodes.munch.model.data


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("number")
    val number: Int = 0,
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("results")
    val results: List<Result> = emptyList(),
    @SerializedName("totalResults")
    val totalResults: Int = 0
)