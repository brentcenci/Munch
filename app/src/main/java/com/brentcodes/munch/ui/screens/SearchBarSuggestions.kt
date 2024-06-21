package com.brentcodes.munch.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarSuggestions(modifier: Modifier = Modifier, suggestion: String = "Suggestion", onClick: (String) -> Unit = { }) {
    Box(modifier.fillMaxWidth().height(80.dp).clickable { onClick(suggestion) }, contentAlignment = Alignment.Center) {
        Text(suggestion)
    }
}