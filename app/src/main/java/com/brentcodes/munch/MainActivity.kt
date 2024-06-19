package com.brentcodes.munch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brentcodes.munch.ui.redesign.CleanMainScreen
import com.brentcodes.munch.ui.theme.LightGrey
import com.brentcodes.munch.ui.theme.MainGreen
import com.brentcodes.munch.ui.theme.MunchTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MunchTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier.height(100.dp),
                            containerColor = Color.White
                        ) {
                            NavigationBarItem(label = {Text("Home")}, icon = {Icon(Icons.Default.Home, "Home Icon")}, onClick = {}, selected = true)
                            NavigationBarItem(label = {Text("Search")}, icon = {Icon(Icons.Default.Search, "Home Icon")}, onClick = {}, selected = false)
                            NavigationBarItem(label = {Text("Favourite")}, icon = {Icon(Icons.Default.Favorite, "Home Icon")}, onClick = {}, selected = false)

                        }
                    }
                ) {
                    CleanMainScreen(modifier = Modifier.padding(it))
                }

            }
        }
    }
}