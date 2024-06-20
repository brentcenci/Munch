package com.brentcodes.recipesapplication.model.spoonaculardata

import com.google.gson.annotations.SerializedName


data class Nutrients(

    @SerializedName("name") var name: String? = null,
    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("unit") var unit: String? = null,
    @SerializedName("percentOfDailyNeeds") var percentOfDailyNeeds: Double? = null

)