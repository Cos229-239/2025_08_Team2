package com.example.ravengamingnews

import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ravengamingnews.ui.CreateAccountScreen
import com.example.ravengamingnews.ui.EditAccountScreen
import com.example.ravengamingnews.ui.FeedScreen
import com.example.ravengamingnews.ui.LoginScreen
import com.example.ravengamingnews.ui.SettingsScreen
import com.example.ravengamingnews.ui.TempRouteScreen
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.TopAppBarButtonPR

/**
 * Enum class representing different screens in the app with their associated string resource IDs.
 */
enum class TempNavScreen(@param:StringRes val title: Int) {
    TempRoute(title = R.string.temp_router),
    Feed(title = R.string.feed),
    Login(title = R.string.login),
    CreateAccount(title = R.string.create_account),
    EditAccount(title = R.string.edit_account),
    Settings(title = R.string.settings)
}

@Composable
fun TopAppBarPR(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        TempNavScreen.valueOf(backStackEntry?.destination?.route ?: TempNavScreen.Feed.name)

    Surface(shadowElevation = 16.dp) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        TopAppBarButtonPR(
                            text = stringResource(R.string.feed),
                            onClick = { navController.navigate(TempNavScreen.Feed.name) },
                            modifier.padding(8.dp),
                            selected = currentScreen == TempNavScreen.Feed
                        )
                        TopAppBarButtonPR(
                            text = stringResource(R.string.all),
                            onClick = {},
                            modifier.padding(8.dp)
                        )
                        TopAppBarButtonPR(
                            text = stringResource(R.string.browse),
                            onClick = {},
                            modifier.padding(8.dp)
                        )
                    }

                    LogoImagePR(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 8.dp, end = 16.dp)
                    )
                }
            },
            colors = mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = modifier
        )
    }
}

@Composable
fun TempAppScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBarPR(
                navController = navController,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TempNavScreen.TempRoute.name,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
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
