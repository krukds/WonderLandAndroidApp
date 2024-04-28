package com.example.wonderland

import PreferencesManager
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wonderland.ViewModels.AttractionTicketViewModel
import com.example.wonderland.ViewModels.AttractionViewModel
import com.example.wonderland.ViewModels.EventTicketViewModel
import com.example.wonderland.ViewModels.EventViewModel
import com.example.wonderland.ViewModels.MainTabsViewModel
import com.example.wonderland.ViewModels.RestaurantViewModel
import com.example.wonderland.ViewModels.UserViewModel
import com.example.wonderland.Views.AccountView
import com.example.wonderland.Views.AuthorizationView
import com.example.wonderland.Views.BottomBarView
import com.example.wonderland.Views.DetailAttractionView
import com.example.wonderland.Views.DetailEventView
import com.example.wonderland.Views.DetailRestaurantView
import com.example.wonderland.Views.FilterView
import com.example.wonderland.Views.RestaurantView
import com.example.wonderland.Views.LoginView
import com.example.wonderland.Views.MainView
import com.example.wonderland.Views.SettingsView
import com.example.wonderland.Views.SignupView
import com.example.wonderland.Views.TicketsView

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    val attractionViewModel: AttractionViewModel = viewModel()
    val restaurantViewModel: RestaurantViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    val mainTabsViewModel: MainTabsViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val attractionTicketsViewModel: AttractionTicketViewModel = viewModel()
    val eventTicketsViewModel: EventTicketViewModel = viewModel()
    val preferenceManager = PreferencesManager(LocalContext.current)

    Scaffold(
        bottomBar = { BottomBarView(navController)  }
    ) {
        NavHost(
            modifier = Modifier
                .padding(it),
            navController = navController,
            startDestination = Screen.MainScreen.route,
        ) {
            composable(route = Screen.MainScreen.route) {
                MainView(
                    attractionViewModel,
                    eventViewModel,
                    mainTabsViewModel,
                    navigateToFilter = {
                        navController.navigate(Screen.FilterScreen.route)
                    },
                    navigateToDetailAttraction = {
                        navController.navigate(Screen.DetailAttractionScreen.route
                            .replace(oldValue = "{aId}", newValue = it.toString()))
                    },
                    navigateToDetailEvent = {
                        navController.navigate(Screen.DetailEventScreen.route
                            .replace(oldValue = "{eId}", newValue = it.toString()))
                    }
                )
            }
            composable(route = Screen.RestaurantScreen.route) {
                RestaurantView(
                    restaurantViewModel,
                    navigateToDetailRestaurant = {
                        navController.navigate(Screen.DetailRestaurantScreen.route
                            .replace(oldValue = "{rId}", newValue = it.toString()))
                    }
                )
            }
            composable(route = Screen.DetailRestaurantScreen.route) {navBackStackEntry->
                val rId = navBackStackEntry.arguments?.getString("rId")
                if (rId != null) {
                    DetailRestaurantView(
                        restaurantId = rId.toInt(),
                        restaurantViewModel = restaurantViewModel,
                        onBackNavCLicked = {
                            navController.navigate(Screen.RestaurantScreen.route)
                        }
                    )
                }
            }
            composable(route = Screen.AccountScreen.route) {
                userViewModel.fetchUser(preferenceManager.getData("token", ""))
                if (userViewModel.userState.value.id == -1) {
                    navController.navigate(Screen.AuthorizationScreen.route)
                } else {
                    AccountView(
                        userViewModel = userViewModel,
                        navigateToTickets = {
                            navController.navigate(Screen.TicketsScreen.route.replace(
                                oldValue = "{sId}", newValue = it.toString())
                            )
                        },
                        navigateToTableBookings = {

                        },
                        navigateToSettings = {
                            navController.navigate(Screen.SettingsScreen.route)
                        },
                        navigateToAuthorization = {
                            navController.navigate(Screen.AuthorizationScreen.route)
                        }
                    )
                }
            }
            composable(route = Screen.TicketsScreen.route) { navBackStackEntry->
                val sId = navBackStackEntry.arguments?.getString("sId")
                if (sId != null) {
                    TicketsView(
                        attractionTicketViewModel = attractionTicketsViewModel,
                        eventTicketViewModel = eventTicketsViewModel,
                        userViewModel = userViewModel,
                        onBackNavCLicked = {
                            navController.navigate(Screen.AccountScreen.route)
                        },
                        ticketType = sId.toInt()
                    )
                }
            }
            composable(route = Screen.FilterScreen.route) {
                FilterView(
                    attractionViewModel,
                    navigateToMain = {
                        navController.navigate(Screen.MainScreen.route)
                    })
            }
            composable(route = Screen.DetailAttractionScreen.route) { navBackStackEntry ->
                val aId = navBackStackEntry.arguments?.getString("aId")
                if (aId != null) {
                    DetailAttractionView(
                        attractionId = aId.toInt(),
                        userViewModel,
                        attractionViewModel,
                        attractionTicketsViewModel,
                        onBackNavCLicked = {
                            navController.navigate(Screen.MainScreen.route)
                        },
                        navigateToAuth = {
                            navController.navigate(Screen.AuthorizationScreen.route)
                        }
                    )
                }
            }
            composable(route = Screen.DetailEventScreen.route) {navBackStackEntry ->
                val eId = navBackStackEntry.arguments?.getString("eId")
                if(eId != null) {
                    DetailEventView(
                        eventId = eId.toInt(),
                        userViewModel,
                        eventViewModel,
                        eventTicketsViewModel,
                        onBackNavCLicked = {
                            navController.navigate(Screen.MainScreen.route)
                        },
                        navigateToAuth = {
                            navController.navigate(Screen.AuthorizationScreen.route)
                        }
                    )
                }

            }
            composable(route = Screen.AuthorizationScreen.route) {
                AuthorizationView(
                    navigateToLogin = {
                        navController.navigate(Screen.LoginScreen.route)
                    },
                    navigateToSignup = {
                        navController.navigate(Screen.SignupScreen.route)
                    }
                )
            }
            composable(route = Screen.LoginScreen.route) {
                LoginView(
                    userViewModel,
                    onCloseCLicked = {
                        navController.navigate(Screen.AuthorizationScreen.route)
                    },
                    navigateToSignup = {
                        navController.navigate(Screen.SignupScreen.route)
                    },
                    navigateToAccount = {
                        navController.navigate(Screen.AccountScreen.route)
                    },
                    preferenceManager
                )
            }
            composable(route = Screen.SignupScreen.route) {
                SignupView(
                    userViewModel,
                    onCloseCLicked = {
                        navController.navigate(Screen.AuthorizationScreen.route)
                    },
                    navigateToAccount = {
                        navController.navigate(Screen.AccountScreen.route)
                    },
                    preferenceManager
                )
            }
            composable(route = Screen.SettingsScreen.route) {
                SettingsView(
                    userViewModel,
                    onBackNavCLicked = {
                        navController.navigate(Screen.AccountScreen.route)
                    },
                    navigateToAuth = {
                        navController.navigate(Screen.AuthorizationScreen.route)
                    }
                )
            }
        }
    }
}