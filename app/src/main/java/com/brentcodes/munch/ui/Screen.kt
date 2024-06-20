package com.brentcodes.munch.ui

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Saved : Screen("saved")
}