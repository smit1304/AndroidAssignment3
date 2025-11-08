package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.AddProductScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.AppSelectorScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.EditProductScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.FavoritesScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.HomeScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.moviescreens.AddMovieScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.moviescreens.EditMovieScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.moviescreens.FavoritesMovieScreen
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens.moviescreens.MovieHomeScreen


// Sets up navigation routes for the app
@Composable
fun AppNavigation(navController: NavHostController, windowSizeClass: WindowSizeClass) {
    val isExpanded = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    // Start on home_detail if expanded, otherwise home
    val startDestination = "app_selector"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // The new selector screen
        composable("app_selector") {
            AppSelectorScreen(navController)
        }

        composable("product_home") {
            HomeScreen(navController, windowSizeClass = windowSizeClass)
        }
        composable("home_detail") {
            HomeScreen(navController, windowSizeClass = windowSizeClass)
        }
        composable("add") {
            AddProductScreen(navController)
        }
        composable(
            route = "edit/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            EditProductScreen(
                productId = backStackEntry.arguments?.getInt("productId"),
                navController = navController
            )
        }
        composable("favorites") {
            FavoritesScreen(navController, windowSizeClass = windowSizeClass)
        }

        // *** Movie routes ***
        composable("movie_home") {
            MovieHomeScreen(navController)
        }
        composable("add_movie") {
            AddMovieScreen(navController)
        }
        composable(
            route = "edit_movie/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            EditMovieScreen(
                movieId = backStackEntry.arguments?.getInt("movieId"),
                navController = navController
            )
        }
        composable("favorites_movie") {
            FavoritesMovieScreen(navController)
        }
    }
}
