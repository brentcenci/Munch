package com.brentcodes.munch.ui

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.MyApp.Companion.database
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.data.Result
import com.brentcodes.munch.model.db.RecipeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _savedRecipes: MutableStateFlow<List<RecipeEntity>> = MutableStateFlow(emptyList())
    val savedRecipes = _savedRecipes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    var initSaved = emptyList<RecipeEntity>()
    val saved = database.recipeDao().getAllSavedRecipesFlow()


    init {
        viewModelScope.launch {
            val currentSaved = database.recipeDao().getAllSavedRecipes()
            initSaved = currentSaved
        }
    }

    fun saveRecipe(recipeId: String) {
        println("Saving recipe: $recipeId")
        viewModelScope.launch {
            database.recipeDao().insert(RecipeEntity(recipeId))
        }
    }

    fun unsaveRecipe(recipeId: String) {
        println("Removing saved recipe: $recipeId")
        viewModelScope.launch {
            database.recipeDao().delete(RecipeEntity(recipeId))
        }
    }

    private val _currentRecipe: MutableStateFlow<Result> = MutableStateFlow(Result())
    val currentRecipe = _currentRecipe.stateIn(viewModelScope, SharingStarted.Lazily, Result())

    fun setCurrentRecipe(recipe: Result) {
        _currentRecipe.value = recipe
    }

    /*fun getSavedRecipes() {
        viewModelScope.launch {
            var ids = _savedRecipes.value.joinToString(",")
            println("Ids to search: $ids")
            val response = RecipeApiClient.recipeApiService.getSavedRecipes(ids = ids)
            println("Saved Screen Response: $response")
            _recipes.value = response
        }
    }*/
}