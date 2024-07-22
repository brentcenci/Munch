package com.brentcodes.munch.ui.screens.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.MyApp
import com.brentcodes.munch.MyApp.Companion.database
import com.brentcodes.munch.model.db.AppDatabase
import com.brentcodes.munch.model.db.RecipeDao
import com.brentcodes.munch.model.db.RecipeEntity
import com.brentcodes.munch.model.db.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeScreenViewModel : ViewModel() {

    private val _expandedIngredients: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val expandedIngredients = _expandedIngredients.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _expandedInstructions: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val expandedInstructions = _expandedInstructions.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _expandedNutrition: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val expandedNutrition = _expandedNutrition.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _savedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val savedState = _savedState.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleIngredients() {
        _expandedIngredients.value = !_expandedIngredients.value
    }

    fun toggleInstructions() {
        _expandedInstructions.value = !_expandedInstructions.value
    }

    fun toggleNutrition() {
        _expandedNutrition.value = !_expandedNutrition.value
    }

    fun toggleSavedState() {
        _savedState.value = !_savedState.value
    }

    fun saveRecipe(id: String) {
        viewModelScope.launch {
            val recipeEntity = RecipeEntity(id)
            database.recipeDao().insert(recipeEntity)
        }
    }

    fun unsaveRecipe(id: String) {
        viewModelScope.launch {
            val recipeEntity = RecipeEntity(id)
            database.recipeDao().delete(recipeEntity)
        }
    }

    fun getAllSavedRecipes() {
        viewModelScope.launch {
            val recipes = database.recipeDao().getAllSavedRecipes()
            println(recipes)
        }
    }

    fun deleteAllSavedRecipes() {
        viewModelScope.launch {
            database.recipeDao().deleteAll()
        }
    }



}