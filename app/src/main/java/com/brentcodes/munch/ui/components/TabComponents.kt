package com.brentcodes.munch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SectionTabs(modifier: Modifier = Modifier, tabs: @Composable () -> Unit, tabScreens: List<@Composable () -> Unit>) {
    val selectedIndex = remember { mutableIntStateOf(0) }
    Column {
        TabRow(
            selectedTabIndex = selectedIndex.value,
            containerColor = Color.Green,
            contentColor = Color.Gray
        ) {
            tabs()
        }

        tabScreens[selectedIndex.intValue]
    }


}