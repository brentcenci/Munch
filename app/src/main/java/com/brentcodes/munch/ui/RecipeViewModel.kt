package com.brentcodes.munch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _currentRecipe: MutableStateFlow<Result> = MutableStateFlow(Result())
    val currentRecipe = _currentRecipe.stateIn(viewModelScope, SharingStarted.Lazily, Result())

/*    private val _ingredients: MutableStateFlow<IngredientsResponse> = MutableStateFlow(IngredientsResponse(
        emptyList()))
    val ingredients = _ingredients.stateIn(viewModelScope, SharingStarted.Lazily, IngredientsResponse(
        emptyList()))*/

    fun setCurrentRecipe(recipe: Result) {
        _currentRecipe.value = recipe
/*        viewModelScope.launch {
            try {
                val response = RecipeApiClient.recipeApiService.getIngredients(id = recipe.id!!)
                _ingredients.value = response
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }*/
    }
}