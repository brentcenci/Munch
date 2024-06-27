package com.brentcodes.munch.model.data


import com.google.gson.annotations.SerializedName

data class AutocompleteResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("title")
    val title: String
)