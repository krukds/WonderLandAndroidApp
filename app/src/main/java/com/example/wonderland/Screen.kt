package com.example.wonderland

sealed class Screen(val route: String) {
    object MainScreen : Screen("main-screen")
    object RestaurantScreen: Screen("restaurant-screen")
    object AccountScreen: Screen("account-screen")
    object FilterScreen: Screen("attraction-filter-screen")
    object DetailAttractionScreen: Screen("attraction-detail-screen/{aId}")
    object DetailEventScreen: Screen("event-detail-screen/{eId}")
    object DetailRestaurantScreen: Screen("restaurant-detail-screen/{rId}")
    object AuthorizationScreen: Screen("authorization-screen")
    object LoginScreen: Screen("login-screen")
    object SignupScreen: Screen("signup-screen")
    object TicketsScreen: Screen("tickets-screen/{sId}")
    object SettingsScreen: Screen("settings-screen")
}