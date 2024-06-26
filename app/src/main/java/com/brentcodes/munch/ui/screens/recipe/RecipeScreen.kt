package com.brentcodes.munch.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.theme.LightGrey
import com.brentcodes.munch.ui.theme.LighterGreen
import com.brentcodes.munch.ui.theme.LightestGreen
import com.brentcodes.munch.ui.theme.MainGreen

@Composable
fun RecipeScreen(modifier: Modifier = Modifier, viewModel: RecipeScreenViewModel, recipeViewModel: RecipeViewModel) {
    val recipe by recipeViewModel.currentRecipe.collectAsState()
    val ingredientsExpanded by viewModel.expandedIngredients.collectAsState()
    val instructionsExpanded by viewModel.expandedInstructions.collectAsState()
    val nutritionExpanded by viewModel.expandedNutrition.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            AsyncImage(
                model = recipe.image,
                contentDescription = "Cover Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        item {
            Row(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = recipe.title ?: "Recipe Title",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.FavoriteBorder, "Save Icon")
            }
        }
        item {
            Row(modifier = Modifier
                .padding(20.dp)
                .shadow(10.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Servings")
                    Text("${recipe.servings}", fontSize = 18.sp)
                }
                Text("|", fontSize = 40.sp, color = LightGrey)
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Takes")
                    Text("${recipe.readyInMinutes} mins", fontSize = 18.sp)
                }
            }
        }
        item {
            DropdownSection(
                "Ingredient",
                color = LightestGreen,
                expanded = ingredientsExpanded,
                onClick = { viewModel.toggleIngredients() }
            ) {
                Text("Hey this is expanded")
            }
        }
        item {
            DropdownSection(
                "Instructions",
                color = LighterGreen,
                expanded = instructionsExpanded,
                onClick = { viewModel.toggleInstructions() }
            ) {
                Text("Hey this is expanded")
            }
        }
        item {
            DropdownSection(
                "Nutrition",
                color = MainGreen,
                expanded = nutritionExpanded,
                onClick = { viewModel.toggleNutrition() }
            ) {
                Text("Hey this is expanded")
            }
        }
    }
}

@Composable
fun DropdownSection(title: String, color: Color = LightestGreen, expanded: Boolean, onClick: () -> Unit = { }, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .clickable { onClick() }
                .padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            if (expanded) {
                Icon(Icons.Default.KeyboardArrowUp, "Expanded Icon")
            } else {
                Icon(Icons.Default.KeyboardArrowDown, "Expanded Icon")
            }
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                content()
            }
        }

    }
}