package com.brentcodes.munch.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.brentcodes.munch.ui.theme.LightGrey
import com.brentcodes.recipesapplication.model.spoonaculardata.Results

@Composable
fun RecipeScreen(modifier: Modifier = Modifier, recipeViewModel: RecipeViewModel) {
    val recipe by recipeViewModel.currentRecipe.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = recipe.image,
            contentDescription = "Cover Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(20.dp)) {
            Row {
                Text(
                    text = recipe.title?: "Recipe Title",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.FavoriteBorder, "Save Icon")
            }
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier
                .shadow(10.dp).background(Color.White, shape = RoundedCornerShape(10.dp))
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
            //Ingredients (Maybe expandable lists? Drop down?)
            //Steps
            //Nutrition
        }
    }
}