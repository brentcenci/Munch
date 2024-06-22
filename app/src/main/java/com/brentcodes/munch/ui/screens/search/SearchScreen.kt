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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brentcodes.munch.ui.components.CustomSearchBar
import com.brentcodes.munch.ui.components.RecipeCard
import com.brentcodes.munch.ui.components.RecipeCardTest
import com.brentcodes.munch.ui.components.SearchBarSuggestions

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val padding = PaddingValues(horizontal = 20.dp)
    val vm = remember { SearchScreenViewModel() }
    val query by vm.searchQuery.collectAsState()
    val results by vm.results.collectAsState()
    val suggestions by vm.suggestions.collectAsState()
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                CustomSearchBar(paddingValues = padding, hasFilters = true, value = query, onValueChanged = {value -> vm.setSearchQuery(value) }, onSearch = { vm.search() })
            }
            if (results.number == null && suggestions.isNotEmpty()) {
                items(suggestions) {
                    SearchBarSuggestions(suggestion = it.title)
                }
            }

            if (results.number != null) {
                items(results.results) { result ->
                    RecipeCardTest(result = result)
                }
            }
        }
    }
}