package com.example.ravengamingnews

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ravengamingnews.navigation.AppRoutes
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.AboutScreen
import com.example.ravengamingnews.ui.AllTabContent
import com.example.ravengamingnews.ui.ArticlePage
import com.example.ravengamingnews.ui.BrowseScreenAlt
import com.example.ravengamingnews.ui.EditAccountScreen
import com.example.ravengamingnews.ui.EditAccountViewModel
import com.example.ravengamingnews.ui.FeedScreenAlt
import com.example.ravengamingnews.ui.FiltersScreenAlt
import com.example.ravengamingnews.ui.FiltersViewModel
import com.example.ravengamingnews.ui.SavedScreen
import com.example.ravengamingnews.ui.SupportScreen
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.TopAppBarButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import kotlinx.coroutines.launch

@Composable
private fun TopAppBarPR(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    navController: androidx.navigation.NavHostController
) {
    val currentRoute = navigationViewModel.currentRoute.collectAsState("").value
    val scope = rememberCoroutineScope()

    Surface(shadowElevation = 16.dp) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
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
                            onClick = { navigationViewModel.navigateToMainTab(AppRoutes.HOME_FEED) },
                            modifier.padding(8.dp),
                            selected = currentRoute == AppRoutes.HOME_FEED
                        )
                        TopAppBarButtonPR(
                            text = stringResource(R.string.all),
                            onClick = { navigationViewModel.navigateToMainTab(AppRoutes.HOME_ALL) },
                            modifier.padding(8.dp),
                            selected = currentRoute == AppRoutes.HOME_ALL
                        )
                        TopAppBarButtonPR(
                            text = stringResource(R.string.browse),
                            onClick = { navigationViewModel.navigateToMainTab(AppRoutes.HOME_BROWSE) },
                            modifier.padding(8.dp),
                            selected = currentRoute == AppRoutes.HOME_BROWSE
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
private fun SettingsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClicked: () -> Unit,
) {
    Surface(shadowElevation = 16.dp) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            colors = mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = modifier
        )
    }
}

@Composable
fun HomeScreen(
    drawerState: DrawerState,
) {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val editAccountViewModel: EditAccountViewModel = hiltViewModel()
    val filtersViewModel: FiltersViewModel = hiltViewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AppRoutes.HOME_FEED

    // Define main screen routes (anything else will be considered settings-related)
    val isSettingsRoute = AppRoutes.isSettingsRoute(currentRoute)

    // Set NavController in NavigationViewModel
    navigationViewModel.setNavController(navController)

    Scaffold(
        topBar = {
            if (!isSettingsRoute) {
                TopAppBarPR(
                    navigationViewModel = navigationViewModel,
                    drawerState = drawerState,
                    navController = navController
                )
            } else {
                SettingsTopAppBar(
                    title = stringResource(
                        AppRoutes.getTitleResId(currentRoute)
                    ), onBackClicked = { navigationViewModel.navigateUp() })
            }
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME_FEED,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }) {
            composable(route = AppRoutes.HOME_FEED) {
//                FeedScreen(navigationViewModel, articleListViewModel)
                FeedScreenAlt(
                    navigationViewModel = navigationViewModel, filtersViewModel = filtersViewModel
                )
            }
            composable(route = AppRoutes.HOME_ALL) {
                AllTabContent(navigationViewModel)
            }
            composable(route = AppRoutes.HOME_BROWSE) {
//                BrowseScreen()
                BrowseScreenAlt(
                    filtersViewModel = filtersViewModel, navigationViewModel = navigationViewModel
                )
            }
            composable(route = AppRoutes.SETTINGS_EDIT_ACCOUNT) {
                EditAccountScreen(viewModel = editAccountViewModel)
            }
            composable(route = AppRoutes.SETTINGS_FILTERS) {
//                FiltersScreen()
                FiltersScreenAlt(viewModel = filtersViewModel)
            }
            composable(route = AppRoutes.SETTINGS_SAVED) {
                SavedScreen(navigationViewModel = navigationViewModel)
            }
            composable(route = AppRoutes.SETTINGS_SUPPORT) {
                SupportScreen()
            }
            composable(route = AppRoutes.SETTINGS_ABOUT) {
                AboutScreen()
            }
            composable(
                route = AppRoutes.ARTICLE_DETAILS, arguments = listOf(navArgument("articleId") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId")
                ArticlePage(articleId = articleId)
            }
        }
    }
}

@Preview
@Composable
fun SettingsTopAppBarPreview() {
    RavenGamingNewsTheme {
        SettingsTopAppBar(
            title = stringResource(R.string.account), onBackClicked = {})
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    RavenGamingNewsTheme {
        TopAppBarPR(
            drawerState = DrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed),
            navController = rememberNavController()
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    RavenGamingNewsTheme {
        HomeScreen(
            drawerState = DrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
        )
    }
}
