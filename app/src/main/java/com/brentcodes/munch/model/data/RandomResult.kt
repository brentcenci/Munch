package com.brentcodes.munch.model.data

import com.google.gson.annotations.SerializedName

data class RandomResult(
    @SerializedName("recipes") var recipes: ArrayList<Result> = arrayListOf()
)
