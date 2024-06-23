package com.brentcodes.munch.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.RecipeApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {

    init {
        viewModelScope.launch {
            val results = try {
                RecipeApiClient.recipeApiService.getComplexSearch("chicken")
            } catch (e: Exception) {
                println(e.message)
            }
            println(results.toString())
        }
    }

    fun randomRecipe() {
        viewModelScope.launch {
            try {
                val result = RecipeApiClient.recipeApiService.getRandomRecipe()
                println(result.recipes.first().title)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}