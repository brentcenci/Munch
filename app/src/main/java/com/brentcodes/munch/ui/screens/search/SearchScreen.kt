package com.brentcodes.munch.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brentcodes.munch.ui.components.CustomSearchBar
import com.brentcodes.munch.ui.components.SearchBarSuggestions

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val padding = PaddingValues(horizontal = 20.dp)
    val vm = remember { SearchScreenViewModel() }
    val query by vm.searchQuery.collectAsState()
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            item {
                CustomSearchBar(paddingValues = padding, hasFilters = true, value = query, onValueChanged = {value -> vm.setSearchQuery(value) })
            }
            items(20) {
                SearchBarSuggestions(suggestion = it.toString())
            }
        }
    }
}