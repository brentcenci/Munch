package com.brentcodes.munch.ui.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.brentcodes.munch.ui.CATEGORIES
import com.brentcodes.munch.ui.CUISINES
import com.brentcodes.munch.ui.DIETS
import com.brentcodes.munch.ui.INTOLERANCES
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.Screen
import com.brentcodes.munch.ui.components.CustomSearchBar
import com.brentcodes.munch.ui.components.RecipeCardTest
import com.brentcodes.munch.ui.components.SearchBarSuggestions
import com.brentcodes.munch.ui.screens.home.MainScreenTitleText
import com.brentcodes.munch.ui.theme.LightGrey
import com.brentcodes.munch.ui.theme.MainGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel,
    recipeViewModel: RecipeViewModel,
    navController: NavController
) {
    val padding = PaddingValues(horizontal = 20.dp)
    //val vm = remember { SearchScreenViewModel() }
    val filtersOpen = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.results.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val hasFilters by viewModel.hasFilters.collectAsState()
    val listOfFilters by viewModel.setOfFilters.collectAsState()
    Column(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                CustomSearchBar(
                    paddingValues = padding,
                    hasFilters = true,
                    value = query,
                    onValueChanged = { value -> viewModel.setSearchQuery(value) },
                    onSearch = { viewModel.search() },
                    onFilterClick = { filtersOpen.value = true },
                    filtersActive = filtersOpen.value || hasFilters
                )
            }
            if (results.number == 0 && suggestions.isNotEmpty()) {
                items(suggestions) {
                    SearchBarSuggestions(
                        suggestion = it.title,
                        onClick = { viewModel.setSearchQuery(it) })
                }
            }

            if (results.number != 0) {
                items(results.results) { result ->
                    RecipeCardTest(result = result, onClick = { recipe ->
                        recipeViewModel.setCurrentRecipe(recipe)
                        navController.navigate(Screen.Recipe.route)
                    },
                        isSaved = false,
                        onSave = { recipeViewModel.saveRecipe(it) },
                        onUnsave = { recipeViewModel.unsaveRecipe(it) }
                    )
                }
            }
        }
        FiltersBottomSheet(
            state = bottomSheetState,
            dismiss = {set ->
                filtersOpen.value = false
                viewModel.updateFilters(set)
                      },
            openState = filtersOpen.value,
            currentlySelected = listOfFilters
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterFlowRow(modifier: Modifier = Modifier, filterGroup: Map<String, Int>, currentSelected: Set<String>, toggleFilter: (String) -> Unit = { }) {
    FlowRow(
        maxItemsInEachRow = 4,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        filterGroup.toList().forEach {
            val selected = remember { mutableStateOf(
                it.first in currentSelected
            ) }
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .background(
                        if (selected.value) MainGreen else LightGrey,
                        RoundedCornerShape(10.dp)
                    )
                    .width(80.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        selected.value = !selected.value
                        toggleFilter(it.first)
                    }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(
                        painterResource(id = it.second),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = it.first.replaceFirstChar { it.uppercase() },
                        fontSize = 10.sp,
                        color = if (selected.value) Color.White else Color.Black,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBottomSheet(
    modifier: Modifier = Modifier,
    state: SheetState,
    dismiss: (Set<String>) -> Unit,
    openState: Boolean,
    currentlySelected: Set<String>
) {
    val bottomSheetViewModel = remember { BottomSheetViewModel(currentlySelected) }

    if (openState) {
        ModalBottomSheet(
            onDismissRequest = {
                Log.d("hey", bottomSheetViewModel.getSet().toString())
                dismiss(bottomSheetViewModel.getSet())
                               },
            sheetMaxWidth = Dp.Unspecified

        ) {
            val currentSelected = remember { bottomSheetViewModel.getSet() }
            LazyColumn(
                modifier = Modifier.padding(20.dp),
            ) {
                item { MainScreenTitleText(text = "Categories", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = CATEGORIES, currentSelected = currentSelected,toggleFilter = {bottomSheetViewModel.updateSet(it)})
                }
                item { MainScreenTitleText(text = "Cuisines", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = CUISINES, currentSelected = currentSelected,toggleFilter = {bottomSheetViewModel.updateSet(it)})
                }
                item { MainScreenTitleText(text = "Diet", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = DIETS, currentSelected = currentSelected,toggleFilter = {bottomSheetViewModel.updateSet(it)})
                }
                item { MainScreenTitleText(text = "Intolerances", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = INTOLERANCES,currentSelected = currentSelected, toggleFilter = {bottomSheetViewModel.updateSet(it)})
                }
            }

        }
    }
}