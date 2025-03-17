package com.simple.cats.presentation.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simple.cats.R
import com.simple.cats.presentation.util.BottomNavItem
import com.simple.cats.presentation.viewmodel.BreedFactsViewModel
import com.simple.cats.presentation.viewmodel.BreedViewModel
import com.simple.cats.presentation.viewmodel.ImageSearchViewModel
import com.simple.cats.presentation.views.pages.BreedFactsPage
import com.simple.cats.presentation.views.pages.BreedSearchPage
import com.simple.cats.presentation.views.pages.ImageSearchPage
import com.simple.cats.ui.theme.CatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                        BottomNavigationBarApp()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBarApp() {

    val navController = rememberNavController()
    val breedFactsViewModel = hiltViewModel<BreedFactsViewModel>()
    val breedSearchViewModel = hiltViewModel<BreedViewModel>()
    val imageSearchViewModel = hiltViewModel<ImageSearchViewModel>()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.leaf),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            NavHost(
                navController = navController,
                startDestination = BottomNavItem.ImageSearch.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(BottomNavItem.ImageSearch.route) {
                    ImageSearchPage(imageSearchViewModel)
                }

                composable(BottomNavItem.BreedSearch.route) {
                    BreedSearchPage(breedSearchViewModel)
                }

                composable(BottomNavItem.BreedFacts.route) {
                    BreedFactsPage(breedFactsViewModel)
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        BottomNavItem.ImageSearch,
        BottomNavItem.BreedSearch,
        BottomNavItem.BreedFacts
    )

    NavigationBar {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
