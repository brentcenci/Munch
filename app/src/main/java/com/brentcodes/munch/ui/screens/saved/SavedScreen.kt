package com.brentcodes.munch.ui.screens.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brentcodes.munch.ui.Screen
import com.brentcodes.munch.ui.components.RecipeCardTest

@Composable
fun SavedScreen(modifier: Modifier = Modifier) {
    val viewModel = remember { SavedScreenViewModel() }
    val recipes by viewModel.recipes.collectAsState()
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(recipes) { result ->
                RecipeCardTest(result = result, onClick = {recipe ->
                    /*recipeViewModel.setCurrentRecipe(recipe)
                    navController.navigate(Screen.Recipe.route)*/
                })
            }
        }
    }
}