package com.brentcodes.munch.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RecipeCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(20.dp).clip(RoundedCornerShape(10.dp))
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(100.dp)) //Image
        Column(modifier = Modifier.fillMaxWidth().height(80.dp)) {
            Text("Recipe Name")
            Text("Brief summary of the recipe here, talking about flavours and other components that may educate a user on what to expect")
            Text("Health rating? Calories? Time it takes?")
        }
    }
}