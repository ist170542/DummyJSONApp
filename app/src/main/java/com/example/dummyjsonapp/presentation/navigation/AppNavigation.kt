package com.example.dummyjsonapp.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector

// Define the screens for the bottom navigation
enum class BottomNavScreen {
    LIST,
    FORM
}

// Define the screens for the entire app
enum class AppScreen {
    MAIN,
    DETAILS
}

// Data class for bottom navigation items
data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

// Sealed class for navigation items (for type safety)
sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem("splash")
    object Main : NavigationItem("main")
    object Details : NavigationItem("details/{productId}") {
        fun createRoute(productId: Int) = "details/$productId"
    }
}