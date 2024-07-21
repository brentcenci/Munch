package com.brentcodes.munch.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.data.AutocompleteResponse
import com.brentcodes.munch.model.data.SearchResult
import com.brentcodes.munch.ui.CATEGORIES
import com.brentcodes.munch.ui.CUISINES
import com.brentcodes.munch.ui.DIETS
import com.brentcodes.munch.ui.INTOLERANCES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery = _searchQuery.stateIn(scope = viewModelScope, SharingStarted.Lazily, "")

    private val _selectedCategories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedCategories =
        _selectedCategories.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedCuisines: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedCuisines =
        _selectedCuisines.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedDiets: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val selectedDiets = _selectedDiets.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedIntolerances: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val selectedIntolerances =
        _selectedIntolerances.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val hasFilters: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val setOfFilters: MutableStateFlow<Set<String>> = MutableStateFlow<Set<String>>(emptySet())

    private val _results: MutableStateFlow<SearchResult> = MutableStateFlow(SearchResult())
    val results = _results.stateIn(viewModelScope, SharingStarted.Lazily, SearchResult())

    private val _suggestions: MutableStateFlow<AutocompleteResponse> = MutableStateFlow(
        AutocompleteResponse()
    )
    val suggestions =
        _suggestions.stateIn(viewModelScope, SharingStarted.Lazily, AutocompleteResponse())

    init {
        println("Doing Init for Search Screen")
        combine(
            _selectedCategories,
            _selectedDiets,
            _selectedIntolerances,
            _selectedCuisines
        ) { category, diets, intolerances, cuisines ->
            hasFilters.value =
                category.isNotEmpty() || diets.isNotEmpty() || intolerances.isNotEmpty() || cuisines.isNotEmpty()
            setOfFilters.value = (category + diets + intolerances + cuisines).toSet()

        }.launchIn(viewModelScope)




    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            try {
                val response: AutocompleteResponse =
                    RecipeApiClient.recipeApiService.getAutocompleteSuggestions(query = query)
                _suggestions.value = response
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun search() {
        val intolerances = _selectedIntolerances.value.joinToString(",")
        val cuisines = _selectedCuisines.value.joinToString(",")
        val categories = _selectedCategories.value.joinToString(",")
        val diets = _selectedDiets.value.joinToString(",")
        viewModelScope.launch {
            val response: SearchResult = try {
                RecipeApiClient.recipeApiService.getComplexSearch(
                    query = searchQuery.value,
                    intolerances = intolerances,
                    cuisine = cuisines,
                    diet = diets,
                    category = categories
                )
            } catch (e: Exception) {
                println(e.message)
                SearchResult()
            }
            _results.value = response
        }
    }

    fun updateFilters(set: Set<String>) {
        val selectedCuisine = mutableListOf<String>()
        val selectedCategory = mutableListOf<String>()
        val selectedDiet = mutableListOf<String>()
        val selectedIntoler = mutableListOf<String>()

        _selectedCuisines.value = selectedCuisine
        _selectedCategories.value = selectedCategory
        _selectedDiets.value = selectedDiet
        _selectedIntolerances.value = selectedIntoler
        set.forEach {
            if (CUISINES.containsKey(it)) {
                selectedCuisine.add(it)
            }
            if (CATEGORIES.containsKey(it)) {
                selectedCategory.add(it)
            }
            if (DIETS.containsKey(it)) {
                selectedDiet.add(it)
            }
            if (INTOLERANCES.containsKey(it)) {
                selectedIntoler.add(it)
            }
        }

        _selectedCuisines.value = selectedCuisine
        _selectedCategories.value = selectedCategory
        _selectedDiets.value = selectedDiet
        _selectedIntolerances.value = selectedIntoler
    }

    fun getAllFilters(): Set<String> {
        val set = mutableSetOf<String>()
        set.addAll(_selectedCategories.value)
        set.addAll(_selectedCuisines.value)
        set.addAll(_selectedDiets.value)
        set.addAll(_selectedIntolerances.value)

        return set
    }

}