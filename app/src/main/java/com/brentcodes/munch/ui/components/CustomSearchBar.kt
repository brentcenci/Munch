package com.brentcodes.munch.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brentcodes.munch.ui.theme.MainGreen

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp),
    hasFilters: Boolean = false,
    filtersOpen: Boolean = false,
    onFilterClick: () -> Unit = {},
    readOnly: Boolean = false,
    value: String = "",
    onValueChanged: (String) -> Unit = {},
    onClick: () -> Unit = { }

) {
    val source = remember { MutableInteractionSource() }
    if (source.collectIsPressedAsState().value) {
        onClick()
    }

    val filterIconColors = if (!filtersOpen) {
        IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent, contentColor = Color.Black)
    } else {
        IconButtonDefaults.iconButtonColors(containerColor = MainGreen, contentColor = Color.White)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(paddingValues = paddingValues)
    ) {
        OutlinedTextField(
            readOnly = readOnly,
            value = value,
            onValueChange = {value -> onValueChanged(value) } ,
            maxLines = 1,
            label = { Text("Search for a recipe") },
            leadingIcon = { Icon(Icons.Rounded.Search, "Search Icon") },
            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                if (hasFilters) {
                    IconButton(
                        onClick = { onFilterClick() }, modifier = Modifier
                            .clip(
                                RoundedCornerShape(50)
                            )
                            .size(40.dp), colors = filterIconColors
                    ) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu Icon")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            interactionSource = source
        )
    }
}