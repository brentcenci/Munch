package com.brentcodes.munch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.MyApp.Companion.database
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.data.Result
import com.brentcodes.munch.model.db.RecipeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _savedRecipes: MutableStateFlow<List<RecipeEntity>> = MutableStateFlow(emptyList())
    val savedRecipes = _savedRecipes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val saved = database.recipeDao().getAllSavedRecipesFlow()

    private val _currentRecipe: MutableStateFlow<Result> = MutableStateFlow(Result())
    val currentRecipe = _currentRecipe.stateIn(viewModelScope, SharingStarted.Lazily, Result())

    fun setCurrentRecipe(recipe: Result) {
        _currentRecipe.value = recipe
    }
}