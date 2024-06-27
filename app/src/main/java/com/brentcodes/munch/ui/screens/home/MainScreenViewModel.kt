package com.brentcodes.munch.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.data.SearchResult
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {

    private val _quick15: MutableStateFlow<SearchResult> = MutableStateFlow(SearchResult())
    val quick15 = _quick15.stateIn(viewModelScope, SharingStarted.Lazily, SearchResult())

    private val _desserts: MutableStateFlow<SearchResult> = MutableStateFlow(SearchResult())
    val desserts = _desserts.stateIn(viewModelScope, SharingStarted.Lazily, SearchResult())

    private val _vegan: MutableStateFlow<SearchResult> = MutableStateFlow(SearchResult())
    val vegan = _vegan.stateIn(viewModelScope, SharingStarted.Lazily, SearchResult())

    init {
        viewModelScope.launch {
            println("Doing Init for Main Screen")
            try {
                val quick = RecipeApiClient.recipeApiService.getQuickMeals15()
                println("Main Screen, View Model, Quick 15: $quick")
                _quick15.value = quick
                val desserts = RecipeApiClient.recipeApiService.getDessertMeals()
                println("Main Screen, View Model, Quick 15: $desserts")
                _desserts.value = desserts
                val vegan = RecipeApiClient.recipeApiService.getVeganMeals()
                println("Main Screen, View Model, Quick 15: $vegan")
                _vegan.value = vegan
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun randomRecipe(recipeViewModel: RecipeViewModel, navController: NavController) {
        viewModelScope.launch {
            try {
                val recipe = RecipeApiClient.recipeApiService.getRandomRecipe().recipes.first()
                println(recipe.title)
                recipeViewModel.setCurrentRecipe(recipe)
                navController.navigate(Screen.Recipe.route)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}