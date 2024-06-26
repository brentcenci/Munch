package com.brentcodes.munch.ui.screens.home

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.RecipeApiService
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.Screen
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainScreenViewModel: ViewModel() {

    private val _quick15: MutableStateFlow<SpoonacularResult> = MutableStateFlow(SpoonacularResult())
    val quick15 = _quick15.stateIn(viewModelScope, SharingStarted.Lazily, SpoonacularResult())

    private val _desserts: MutableStateFlow<SpoonacularResult> = MutableStateFlow(SpoonacularResult())
    val desserts = _desserts.stateIn(viewModelScope, SharingStarted.Lazily, SpoonacularResult())

    private val _vegan: MutableStateFlow<SpoonacularResult> = MutableStateFlow(SpoonacularResult())
    val vegan = _vegan.stateIn(viewModelScope, SharingStarted.Lazily, SpoonacularResult())

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