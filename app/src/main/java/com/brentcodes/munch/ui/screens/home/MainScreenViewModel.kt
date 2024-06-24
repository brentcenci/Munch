package com.brentcodes.munch.ui.screens.home

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.RecipeApiService
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {

    private val _quick15: MutableStateFlow<SpoonacularResult> = MutableStateFlow(SpoonacularResult())
    val quick15 = _quick15.stateIn(viewModelScope, SharingStarted.Lazily, SpoonacularResult())

    init {
        viewModelScope.launch {
            println("Doing Init for Main Screen")
            try {
                val results = RecipeApiClient.recipeApiService.getQuickMeals15()
                println("Main Screen, View Model, Quick 15: $results")
                _quick15.value = results
            } catch (e: Exception) {
                println(e.message)
            }
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