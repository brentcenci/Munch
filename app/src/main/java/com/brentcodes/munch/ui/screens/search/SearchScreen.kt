package com.brentcodes.munch.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.brentcodes.munch.ui.screens.CustomSearchBar
import com.brentcodes.munch.ui.screens.home.LogoSection
import com.brentcodes.munch.ui.screens.home.SearchBarSection

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val padding = PaddingValues(horizontal = 20.dp)
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            item { CustomSearchBar(paddingValues = padding, hasFilters = true) }
        }
    }
}