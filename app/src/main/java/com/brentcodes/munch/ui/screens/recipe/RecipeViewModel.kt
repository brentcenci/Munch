package com.brentcodes.munch.ui.screens.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class RecipeViewModel : ViewModel() {

    private val _currentRecipe: MutableStateFlow<Results> = MutableStateFlow(Results())
    val currentRecipe = _currentRecipe.stateIn(viewModelScope, SharingStarted.Lazily, Results())

    fun setCurrentRecipe(recipe: Results) {
        _currentRecipe.value = recipe
    }
}