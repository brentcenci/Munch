package com.brentcodes.munch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.brentcodes.munch.model.db.AppDatabase
import com.brentcodes.munch.ui.screens.home.CleanMainScreen
import com.brentcodes.munch.ui.Screen
import com.brentcodes.munch.ui.screens.home.MainScreenViewModel
import com.brentcodes.munch.ui.screens.recipe.RecipeScreen
import com.brentcodes.munch.ui.RecipeViewModel
import com.brentcodes.munch.ui.screens.recipe.RecipeScreenViewModel
import com.brentcodes.munch.ui.screens.saved.SavedScreen
import com.brentcodes.munch.ui.screens.search.SearchScreen
import com.brentcodes.munch.ui.screens.search.SearchScreenViewModel
import com.brentcodes.munch.ui.theme.MunchTheme

class MainActivity : ComponentActivity() {
/*
    companion object {
        lateinit var database: AppDatabase
            private set
    }*/


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()*/

        enableEdgeToEdge()
        setContent {
            MunchTheme {
                val navController = rememberNavController()
                var selectedRoute by remember { mutableStateOf(navController.currentDestination?.route) }
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    selectedRoute = destination.route
                }
                val recipeViewModel: RecipeViewModel = viewModel()
                val showBottomBarOnRecipes = true
                Scaffold(
                    bottomBar = {
                        if (selectedRoute != Screen.Recipe.route || showBottomBarOnRecipes) {
                            NavigationBar(
                                modifier = Modifier.height(100.dp),
                                containerColor = Color.White
                            ) {
                                NavigationBarItem(alwaysShowLabel = false, label = {Text("Home")}, icon = {Icon(Icons.Default.Home, "Home Icon")}, onClick = {
                                    if (selectedRoute != Screen.Home.route) {
                                        navController.navigate(Screen.Home.route) /*{ popUpTo(Screen.Home.route) { saveState = true } }*/
                                    }


                                }, selected = (selectedRoute == Screen.Home.route))
                                NavigationBarItem(alwaysShowLabel = false, label = {Text("Search")}, icon = {Icon(Icons.Default.Search, "Search Icon")}, onClick = {
                                    if (selectedRoute != Screen.Search.route) {
                                        navController.navigate(Screen.Search.route)
                                    }
                                }, selected = (selectedRoute == Screen.Search.route))
                                NavigationBarItem(alwaysShowLabel = false, label = {Text("Saved")}, icon = {Icon(Icons.Default.Favorite, "Saved Icon")}, onClick = {
                                    if (selectedRoute != Screen.Saved.route) {
                                        navController.navigate(Screen.Saved.route)
                                    }
                                }, selected = (selectedRoute == Screen.Saved.route))
                            }
                        }
                    }
                ) { padding ->
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(route = Screen.Home.route) {
                            val viewModel: MainScreenViewModel = ViewModelProvider(navController.getViewModelStoreOwner(navController.graph.id))[MainScreenViewModel::class.java]
                            //val viewModel: MainScreenViewModel = viewModel()
                            CleanMainScreen(modifier = Modifier.padding(padding), navController = navController, viewModel = viewModel, recipeViewModel = recipeViewModel)
                        }
                        composable(route = Screen.Search.route) {
                            val viewModel: SearchScreenViewModel = ViewModelProvider(navController.getViewModelStoreOwner(navController.graph.id))[SearchScreenViewModel::class.java]
                            SearchScreen(modifier = Modifier.padding(padding), viewModel = viewModel, recipeViewModel = recipeViewModel, navController = navController)
                        }
                        composable(route = Screen.Saved.route) {
                            SavedScreen(modifier = Modifier.padding(padding))
                        }
                        composable(route = Screen.Recipe.route) {
                            val viewModel: RecipeScreenViewModel = ViewModelProvider(navController.getViewModelStoreOwner(navController.graph.id))[RecipeScreenViewModel::class.java]
                            RecipeScreen(modifier = Modifier.padding(padding), navController = navController, viewModel = viewModel, recipeViewModel = recipeViewModel)
                        }
                    }

                }

            }
        }
    }
}