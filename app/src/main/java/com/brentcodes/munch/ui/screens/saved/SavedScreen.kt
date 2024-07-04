package com.brentcodes.munch.ui.screens.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun SavedScreen(modifier: Modifier = Modifier) {
    val viewModel = SavedScreenViewModel()
    val recipes by viewModel.recipes.collectAsState()
    Column(modifier.fillMaxSize()) {
        Text("This is the Saved screen")
        recipes.forEach {
            it.title
        }
    }
}