package com.brentcodes.munch.model.spoonacular


import com.google.gson.annotations.SerializedName

data class IngredientsResponse(
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>
)