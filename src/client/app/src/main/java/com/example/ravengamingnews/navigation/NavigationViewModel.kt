package com.example.ravengamingnews.navigation

import androidx.compose.runtime.mutableStateMapOf
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

    private val _clickedArticles = mutableStateMapOf<Int, Boolean>()
    val clickedArticles: Map<Int, Boolean> get() = _clickedArticles

    fun setNavController(navController: NavController) {
        _navController = navController
    }

    fun navigateTo(route: String) {
        _navController?.navigate(route)
    }

    fun navigateUp() {
        _navController?.navigateUp()
    }

    fun popBackStack() {
        _navController?.popBackStack()
    }

    fun markClicked(articleId: Int) {
        _clickedArticles[articleId] = true
    }
}
