package com.brentcodes.munch.model.spoonacular

import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.google.gson.annotations.SerializedName

data class RandomResult(
    @SerializedName("recipes") var recipes: ArrayList<Results> = arrayListOf()
)
