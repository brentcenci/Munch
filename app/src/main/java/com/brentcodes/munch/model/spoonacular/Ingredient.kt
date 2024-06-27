package com.brentcodes.munch.model.spoonacular


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("amount")
    val amount: Amount,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)