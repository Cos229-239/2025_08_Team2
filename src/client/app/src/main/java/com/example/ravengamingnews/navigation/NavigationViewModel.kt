package com.example.ravengamingnews.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel to centralize navigation logic and reduce the need to pass NavController
 * through multiple layers of composables.
 */
@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

    private var _navController: NavController? = null

    fun setNavController(navController: NavController) {
        _navController = navController
    }

    fun navigateTo(route: String) {
        _navController?.navigate(route)
    }

    /**
     * Navigate to a main tab destination with proper navigation options
     * to ensure consistent tab selection behavior.
     */
    fun navigateToMainTab(route: String) {
        _navController?.let { navController ->
            navController.navigate(route) {
                // Pop up to the start destination to avoid building up a large stack
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    }

    fun navigateUp() {
        _navController?.navigateUp()
    }

    fun popBackStack() {
        _navController?.popBackStack()
    }

    fun navigateToFeed(topic: String) {
        _navController?.let { navController ->
            navController.popBackStack(route = AppRoutes.HOME_BROWSE, inclusive = false)
            navController.navigate("feed/$topic") {
                launchSingleTop = true
            }
        }
    }
}
