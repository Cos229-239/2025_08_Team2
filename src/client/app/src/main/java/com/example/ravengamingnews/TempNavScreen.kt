package com.example.ravengamingnews

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ravengamingnews.ui.CreateAccountScreen
import com.example.ravengamingnews.ui.EditAccountScreen
import com.example.ravengamingnews.ui.FeedScreen
import com.example.ravengamingnews.ui.LoginScreen
import com.example.ravengamingnews.ui.SettingsScreen
import com.example.ravengamingnews.ui.TempRouteScreen

/**
 * Enum class representing different screens in the app with their associated string resource IDs.
 */
enum class TempNavScreen(val route: Int) {
    TempRoute(R.string.temp_router),
    Feed(R.string.feed),
    Login(R.string.login),
    CreateAccount(R.string.create_account),
    EditAccount(R.string.edit_account),
    Settings(R.string.settings)
    // Add other screens here
}

@Composable
fun TempAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "App Bar") },
        colors = mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
    )
}

@Composable
fun TempAppScreen(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TempNavScreen.valueOf(backStackEntry?.destination?.route ?: TempNavScreen.Feed.name)
    Scaffold(
        topBar = {
            TempAppBar()
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TempNavScreen.TempRoute.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = TempNavScreen.TempRoute.name) {
                TempRouteScreen(
                    onFeedClick = { navController.navigate(TempNavScreen.Feed.name) },
                    onLoginClick = { navController.navigate(TempNavScreen.Login.name) },
                    onCreateAccountClick = { navController.navigate(TempNavScreen.CreateAccount.name) },
                    onEditAccountClick = { navController.navigate(TempNavScreen.EditAccount.name) },
                    onSettingsClick = { navController.navigate(TempNavScreen.Settings.name) }
                )
            }
            composable(route = TempNavScreen.Feed.name) {
                FeedScreen()
            }
            composable(route = TempNavScreen.Login.name) {
                LoginScreen()
            }
            composable(route = TempNavScreen.CreateAccount.name) {
                CreateAccountScreen()
            }
            composable(route = TempNavScreen.Settings.name) {
                SettingsScreen()
            }
            composable(route = TempNavScreen.EditAccount.name) {
                EditAccountScreen()
            }
        }
    }
}
