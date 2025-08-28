package com.example.ravengamingnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ravengamingnews.ui.components.TopAppBarPR
import com.example.ravengamingnews.ui.SettingsDrawer
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RavenGamingNewsTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route

                val selectedTab = when (currentDestination) {
                    "feed" -> "FEED"
                    "all" -> "Login"
                    "browse" -> "Login"
                    else -> null
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = { SettingsDrawer(
                        navController = navController,
                        drawerState = drawerState,
                    ) },
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBarPR(
                                selectedTab = selectedTab,
                                onTabSelected = { tab ->
                                    navController.navigate(tab)
                                },
                                onMenuClick = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                    ) { innerPadding ->
                        TempAppScreen(navController,
                        modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RavenGamingNewsTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = { SettingsDrawer(
                navController = navController,
                drawerState = drawerState,
            ) },
        ) {
            TempAppScreen(navController)
        }
    }
}
