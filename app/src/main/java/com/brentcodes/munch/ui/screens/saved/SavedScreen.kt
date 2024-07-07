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
import androidx.navigation.NavController
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.Screen
import com.brentcodes.munch.ui.components.RecipeCardTest

@Composable
fun SavedScreen(modifier: Modifier = Modifier, recipeViewModel: RecipeViewModel, navController: NavController) {
    val saved by recipeViewModel.saved.collectAsState(initial = recipeViewModel.initSaved)
    val viewModel = remember { SavedScreenViewModel(saved) }
    val recipes by viewModel.recipes.collectAsState()
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                saved.forEach { Text(it.id) }
            }
            items(recipes) { result ->
                RecipeCardTest(result = result,
                    onClick = {
                        recipe ->

                        recipeViewModel.setCurrentRecipe(recipe)
                        navController.navigate(Screen.Recipe.route)
                },
                    isSaved = saved.map { it.id }.any { it == result.id.toString() },
                    onSave = { recipeViewModel.saveRecipe(it) },
                    onUnsave = { recipeViewModel.unsaveRecipe(it) })
            }
        }
    }
}