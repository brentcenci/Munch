package com.brentcodes.munch.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.RecipeApiService
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery = _searchQuery.stateIn(scope = viewModelScope, SharingStarted.Lazily, "")

    private val _selectedCategories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedCategories = _selectedCategories.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedCuisines: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedCuisines = _selectedCuisines.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedDiets: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedDiets = _selectedDiets.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedIntolerances: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedIntolerances = _selectedIntolerances.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _results: MutableStateFlow<SpoonacularResult> = MutableStateFlow(SpoonacularResult())
    val results = _results.stateIn(viewModelScope, SharingStarted.Lazily, SpoonacularResult())

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun search() {
        viewModelScope.launch {
            val response: SpoonacularResult = try {
                RecipeApiClient.recipeApiService.getComplexSearch(query = searchQuery.value)
            } catch (e: Exception) {
                println(e.message)
                SpoonacularResult()
            }
            _results.value = response
        }
    }
}