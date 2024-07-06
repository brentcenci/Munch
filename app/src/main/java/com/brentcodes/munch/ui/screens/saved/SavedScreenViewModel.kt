package com.brentcodes.munch.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brentcodes.munch.MyApp.Companion.database
import com.brentcodes.munch.model.RecipeApiClient
import com.brentcodes.munch.model.data.RandomResult
import com.brentcodes.munch.model.data.SavedResponse
import com.brentcodes.munch.model.data.SearchResult
import com.brentcodes.munch.model.db.RecipeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedScreenViewModel(saved : List<RecipeEntity>) : ViewModel() {

    //Cant pass in the saved list like this, only passed once at empty.

    private val _recipes: MutableStateFlow<SavedResponse> = MutableStateFlow(SavedResponse())
    val recipes = _recipes.stateIn(viewModelScope, SharingStarted.Eagerly, SavedResponse())

    init {
        viewModelScope.launch {
            var ids = ""
            saved.forEach {
                ids += it.id + ","
            }
            println("Ids to search: $ids")
            val response = RecipeApiClient.recipeApiService.getSavedRecipes(ids = ids)
            println("Saved Screen Response: $response")
            _recipes.value = response
        }
    }
}

