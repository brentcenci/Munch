package com.brentcodes.munch.ui.screens.recipe

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.brentcodes.munch.model.data.AnalyzedInstruction
import com.brentcodes.munch.model.data.Ingredient
import com.brentcodes.munch.model.data.IngredientX
import com.brentcodes.munch.model.data.NutrientX
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.components.SectionTabs
import com.brentcodes.munch.ui.theme.DarkGrey
import com.brentcodes.munch.ui.theme.DarkerGreen
import com.brentcodes.munch.ui.theme.LightGrey
import com.brentcodes.munch.ui.theme.LighterGreen
import com.brentcodes.munch.ui.theme.LightestGreen
import com.brentcodes.munch.ui.theme.MainGreen

@Composable
fun RecipeScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: RecipeScreenViewModel, recipeViewModel: RecipeViewModel) {
    val recipe by recipeViewModel.currentRecipe.collectAsState()
    val ingredientsExpanded by viewModel.expandedIngredients.collectAsState()
    val instructionsExpanded by viewModel.expandedInstructions.collectAsState()
    val nutritionExpanded by viewModel.expandedNutrition.collectAsState()
    if (recipe.vegan != null) {
        println(recipe)
    }

    val padding = PaddingValues(horizontal = 20.dp)

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            Button(onClick = { recipe.id?.toString()?.let { viewModel.saveRecipe(it) } }) { Text("save recipe") }
        }
        item {
            Button(onClick = {viewModel.getAllSavedRecipes()}) { Text("show all recipe") }
        }
        item {
            Button(onClick = {viewModel.deleteAllSavedRecipes()}) { Text("delete all recipe") }
        }
        item {
            Box {
                AsyncImage(
                    model = recipe.image,
                    contentDescription = "Cover Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(modifier = Modifier.padding(20.dp).align(Alignment.TopStart), onClick = { navController.popBackStack() }, colors = IconButtonDefaults.iconButtonColors(containerColor = DarkGrey.copy(alpha = 0.5f))) {
                    Icon(Icons.Default.KeyboardArrowLeft, "Back Button", tint = Color.White, modifier = Modifier.size(40.dp))
                }
            }

        }
        item {
            Spacer(Modifier.height(20.dp))
        }
        item {
            Row(modifier = Modifier.padding(padding)) {
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
            Row(modifier = Modifier.padding(padding)) {
                Text(
                    "Recipe by " + recipe.sourceName,
                    color = DarkGrey,
                    fontSize = 14.sp
                )
            }
        }
        item {
            Spacer(Modifier.height(20.dp))
        }
        item {
            Row(modifier = Modifier
                .padding(padding)
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
            Spacer(Modifier.height(20.dp))
        }

        /*item {
            val selectedIndex = remember { mutableIntStateOf(0) }
            TabRow(selectedTabIndex = selectedIndex.value, contentColor = Color.White, containerColor = Color.Green) {
                Tab(
                    selected = selectedIndex.value == 0,
                    onClick = { selectedIndex.value = 0 },
                    text = { Text("Ingredients") }
                )
                Tab(
                    selected = selectedIndex.value == 1,
                    onClick = { selectedIndex.value = 1 },
                    text = { Text("Instructions") }
                )
                Tab(
                    selected = selectedIndex.value == 2,
                    onClick = { selectedIndex.value = 2 },
                    text = { Text("Nutrition") }
                )
            }
            when (selectedIndex.value) {
                0 -> recipe.nutrition?.ingredients?.let { IngredientsSection(ingredients = it) }
                1 -> recipe.analyzedInstructions?.let { InstructionsSection(instructions = it) }
                2 -> recipe.nutrition?.ingredients?.let { IngredientsSection(ingredients = it) }
            }
        }

        item {
            Spacer(Modifier.height(20.dp))
        }*/

        item {
            DropdownSection(
                "Ingredients",
                color = LightestGreen,
                expanded = ingredientsExpanded,
                onClick = { viewModel.toggleIngredients() }
            ) {
                recipe.nutrition?.ingredients?.let { IngredientsSection(ingredients = it) }
            }
        }
        item {
            DropdownSection(
                "Instructions",
                color = LighterGreen,
                colorAbove = LightestGreen,
                expanded = instructionsExpanded,
                onClick = { viewModel.toggleInstructions() }
            ) {
                recipe.analyzedInstructions?.let { InstructionsSection(instructions = it) }
            }
        }
        item {
            DropdownSection(
                "Nutrition",
                color = MainGreen,
                colorAbove = LighterGreen,
                last = false,
                expanded = nutritionExpanded,
                onClick = { viewModel.toggleNutrition() }
            ) {
                recipe.nutrition?.nutrients?.let { NutritionSection(nutritions = it) }
            }
        }
        item {
            DropdownSection(
                "Darker",
                contentColor = Color.White,
                color = DarkGrey,
                colorAbove = MainGreen,
                last = false,
                expanded = true,
                onClick = {  }
            ) {
                Text("Hey this is expanded")
            }
        }
    }
}

@Composable
fun DropdownSection(title: String, contentColor: Color = Color.Black, color: Color = LightestGreen, colorAbove: Color = Color.Transparent, last: Boolean = false, expanded: Boolean, onClick: () -> Unit = { }, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(
                shape = if (last) RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ) else RectangleShape
            )
            .background(colorAbove)
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
                color = contentColor,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            if (expanded) {
                Icon(Icons.Default.KeyboardArrowUp, "Expanded Icon", tint = contentColor)
            } else {
                Icon(Icons.Default.KeyboardArrowDown, "Expanded Icon", tint = contentColor)
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

@Composable
fun IngredientsSection(modifier: Modifier = Modifier, ingredients : List<IngredientX>) {
    Column {
        ingredients.forEach { ingredient ->
            Row {
                Text("${ingredient.amount} ${ingredient.unit}")
                Spacer(modifier = Modifier.weight(1f))
                Text(ingredient.name)
            }
        }
    }
}


@Composable
fun InstructionsSection(modifier: Modifier = Modifier, instructions : List<AnalyzedInstruction>) {
    Column {
        instructions.forEach { instruction ->
            instruction.steps.forEach { step ->
                Row{
                    Text(step.number.toString() + " ")
                    Text(step.step)
                }
            }
        }
    }
}

@Composable
fun NutritionSection(modifier: Modifier = Modifier, nutritions : List<NutrientX>) {
    Column {
        nutritions.forEach { nutrient ->
            Row {
                Text("${nutrient.amount} ${nutrient.unit}")
                Spacer(modifier = Modifier.weight(1f))
                Text(nutrient.name)
            }
        }
    }
}