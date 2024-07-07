package com.brentcodes.munch.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.brentcodes.munch.R
import com.brentcodes.munch.model.data.Result
import com.brentcodes.munch.model.data.SearchResult
import com.brentcodes.munch.model.db.RecipeEntity
import com.brentcodes.munch.ui.CATEGORIES
import com.brentcodes.munch.ui.CUISINES
import com.brentcodes.munch.ui.DIETS
import com.brentcodes.munch.ui.INTOLERANCES
import com.brentcodes.munch.ui.Screen
import com.brentcodes.munch.ui.components.CustomSearchBar
import com.brentcodes.munch.ui.components.RecipeCardTest
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.theme.DarkGrey
import com.brentcodes.munch.ui.theme.LightGrey
import com.brentcodes.munch.ui.theme.MainGreen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleanMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainScreenViewModel,
    recipeViewModel: RecipeViewModel
) {
    val saved by recipeViewModel.saved.collectAsState(recipeViewModel.initSaved)
    val padding = PaddingValues(horizontal = 20.dp)
    val filtersOpen = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    //val vm : MainScreenViewModel = viewModel()
    val quick15 by viewModel.quick15.collectAsState()
    val desserts by viewModel.desserts.collectAsState()
    val vegan by viewModel.vegan.collectAsState()
    Column(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { LogoSection(paddingValues = padding) }
            item {
                CustomSearchBar(
                    paddingValues = padding,
                    hasFilters = false,
                    readOnly = true,
                    onClick = { navController.navigate(Screen.Search.route) })
            }
            item { CategoriesSection(paddingValues = padding) }
            item {
                RecipesSection(
                    paddingValues = padding,
                    title = "Quick Meals",
                    subtitle = "Make these meals in 15 minutes or less!",
                    recipes = quick15,
                    onClick = { recipe ->
                        recipeViewModel.setCurrentRecipe(recipe)
                        navController.navigate(Screen.Recipe.route)
                    },
                    savedList = saved,
                    onSave = { recipeViewModel.saveRecipe(it) },
                    onUnsave = { recipeViewModel.unsaveRecipe(it) }
                )
            }
            item {
                RandomRecipeSection(paddingValues = padding, onClick = {
                    viewModel.randomRecipe(
                        recipeViewModel = recipeViewModel,
                        navController = navController
                    )
                })
            }
            item {
                RecipesSection(
                    paddingValues = padding,
                    title = "Sweet Treats",
                    subtitle = "Try making these delicious desserts!",
                    recipes = desserts,
                    onClick = { recipe ->
                        recipeViewModel.setCurrentRecipe(recipe)
                        navController.navigate(Screen.Recipe.route)
                    },
                    savedList = saved,
                    onSave = { recipeViewModel.saveRecipe(it) },
                    onUnsave = { recipeViewModel.unsaveRecipe(it) }
                )
            }
            item {
                RecipesSection(
                    paddingValues = padding,
                    title = "Vegan Recipes",
                    subtitle = "Love cows or something!",
                    recipes = vegan,
                    onClick = { recipe ->
                        recipeViewModel.setCurrentRecipe(recipe)
                        navController.navigate(Screen.Recipe.route)
                    },
                    savedList = saved,
                    onSave = { recipeViewModel.saveRecipe(it) },
                    onUnsave = { recipeViewModel.unsaveRecipe(it) }
                )
            }
        }
        FiltersBottomSheet(
            state = bottomSheetState,
            dismiss = { filtersOpen.value = false },
            openState = filtersOpen.value
        )
    }


}

@Composable
fun LogoSection(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues)
    ) {
        Image(
            painter = painterResource(id = R.drawable.munch),
            "Logo",
            colorFilter = ColorFilter.tint(
                MainGreen
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CategoriesSection(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    val selectedCategory = remember { mutableStateOf("main course") }
    MainScreenTitleText(modifier = modifier.padding(paddingValues), text = "Category")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(CATEGORIES.toList()) {
            val isSelected = selectedCategory.value == it.first.lowercase()
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                    .background(
                        if (isSelected) MainGreen else LightGrey,
                        RoundedCornerShape(10.dp)
                    )
                    .width(80.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { selectedCategory.value = it.first.lowercase() }
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
                        color = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }

            }
        }
    }
}

@Composable
fun RecipesSection(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    paddingValues: PaddingValues,
    recipes: SearchResult,
    onClick: (Result) -> Unit,
    savedList: List<RecipeEntity>,
    onSave: (String) -> Unit,
    onUnsave: (String) -> Unit
) {
    MainScreenTitleText(
        modifier = modifier.padding(paddingValues),
        text = title,
        subtitle = subtitle
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(recipes.results) { result ->

            /*val gradient = remember {
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, DarkGrey),
                    startY = 0F,
                    endY = Float.POSITIVE_INFINITY
                )
            }*/
            RecipeCardTest(
                modifier = Modifier.width(200.dp),
                result = result,
                onClick = onClick,
                isSaved = (savedList.map { it.id }.any { it == result.id.toString() }),
                onSave = {onSave(result.id.toString())},
                onUnsave = {onUnsave(result.id.toString())}
            )
        }
    }
}

@Composable
fun RandomRecipeSection(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onClick: () -> Unit = { }
) {
    Column {
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(DarkGrey, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(140.dp)
                .padding(10.dp)
        ) {
            Column {
                Text(text = "Can't decide?", color = Color.White, fontSize = 30.sp)
                Text(text = "Let us make your life easier.", color = Color.White, fontSize = 16.sp)
                Button(
                    onClick =
                    {
                        onClick()
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text("Random Recipe")
                    Icon(Icons.Rounded.PlayArrow, "Arrow Icon")
                }
            }
        }
    }
}

@Composable
fun MainScreenTitleText(
    modifier: Modifier = Modifier,
    text: String,
    viewAll: Boolean = true,
    subtitle: String? = null
) {
    Column(modifier = modifier) {
        Row() {
            Text(
                text = text,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))
            if (viewAll) Text("View all", textDecoration = TextDecoration.Underline)
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterFlowRow(modifier: Modifier = Modifier, filterGroup: Map<String, Int>) {
    FlowRow(
        maxItemsInEachRow = 4,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        filterGroup.toList().forEach {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .background(
                        LightGrey,
                        RoundedCornerShape(10.dp)
                    )
                    .width(80.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { }
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
                        color = Color.Black,
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
    dismiss: () -> Unit,
    openState: Boolean
) {
    if (openState) {
        ModalBottomSheet(
            onDismissRequest = { dismiss() },
            sheetMaxWidth = Dp.Unspecified
        ) {
            LazyColumn(
                modifier = Modifier.padding(20.dp),
            ) {
                item { MainScreenTitleText(text = "Categories", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = CATEGORIES)
                }
                item { MainScreenTitleText(text = "Cuisines", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = CUISINES)
                }
                item { MainScreenTitleText(text = "Diet", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = DIETS)
                }
                item { MainScreenTitleText(text = "Intolerances", viewAll = false) }
                item {
                    FilterFlowRow(filterGroup = INTOLERANCES)
                }
            }

        }
    }
}