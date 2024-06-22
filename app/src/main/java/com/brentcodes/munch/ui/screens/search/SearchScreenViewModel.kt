package com.brentcodes.munch.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

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

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

/*    fun selectCategory(category: String) {

    }*/
}