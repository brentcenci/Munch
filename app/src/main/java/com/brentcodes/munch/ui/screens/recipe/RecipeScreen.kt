package com.brentcodes.munch.ui.screens.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.brentcodes.recipesapplication.model.spoonaculardata.Results

@Composable
fun RecipeScreen(modifier: Modifier = Modifier, recipeViewModel: RecipeViewModel) {
    val recipe by recipeViewModel.currentRecipe.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = recipe.image,
            contentDescription = "Cover Image",
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Row {
            Text(recipe.title?: "Recipe Title", modifier = Modifier.weight(1f))
            Icon(Icons.Default.FavoriteBorder, "Save Icon")
        }
        //Ingredients (Maybe expandable lists? Drop down?)
        //Steps
        //Nutrition
    }
}