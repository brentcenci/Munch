package com.brentcodes.munch.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brentcodes.munch.ui.Screen
import com.brentcodes.munch.ui.components.CustomSearchBar
import com.brentcodes.munch.ui.components.RecipeCardTest
import com.brentcodes.munch.ui.components.SearchBarSuggestions
import com.brentcodes.munch.ui.RecipeViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: SearchScreenViewModel, recipeViewModel: RecipeViewModel, navController : NavController ) {
    val padding = PaddingValues(horizontal = 20.dp)
    //val vm = remember { SearchScreenViewModel() }
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.results.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                CustomSearchBar(paddingValues = padding, hasFilters = true, value = query, onValueChanged = {value -> viewModel.setSearchQuery(value) }, onSearch = { viewModel.search() })
            }
            if (results.number == 0 && suggestions.isNotEmpty()) {
                items(suggestions) {
                    SearchBarSuggestions(suggestion = it.title, onClick = { viewModel.setSearchQuery(it) })
                }
            }

            if (results.number != 0) {
                items(results.results) { result ->
                    RecipeCardTest(result = result, onClick = {recipe ->
                        recipeViewModel.setCurrentRecipe(recipe)
                        navController.navigate(Screen.Recipe.route)
                    })
                }
            }
        }
    }
}