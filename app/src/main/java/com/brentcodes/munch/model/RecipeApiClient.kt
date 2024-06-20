package com.brentcodes.munch.model

import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.spoonacular.com/"
private const val API_KEY =
    "API KEY HERE"

object RetrofitClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object RecipeApiClient {
    val recipeApiService: RecipeApiService by lazy {
        RetrofitClient.retrofit.create(RecipeApiService::class.java)
    }
}

interface RecipeApiService {
    @GET("recipes/complexSearch")
    suspend fun getComplexSearch(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("addRecipeNutrition") addRecipeNutrition: Boolean = true,
        @Query("cuisine") cuisine: String? = null,
        @Query("diet") diet: String? = null,
        @Query("intolerances") intolerances: String? = null,
        @Query("offset") offset: Int? = null
    ) : SpoonacularResult
}