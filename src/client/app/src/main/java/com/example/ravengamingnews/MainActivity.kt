package com.example.ravengamingnews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.CreateAccountScreen
import com.example.ravengamingnews.ui.NoAccountScreen
import com.example.ravengamingnews.ui.LoginScreen
import com.example.ravengamingnews.ui.SettingsDrawer
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RavenGamingNewsTheme {
                val authState by authViewModel.authState.collectAsState()
                when {
                    authState.isLoading -> {
                        LoadingScreen()
                    }

                    authState.isLoggedIn || authState.continuedAsGuest -> {
                        MainApp()
                    }

                    else -> {
                        LoginFlow()
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Set the NavController in the NavigationViewModel
    remember {
        navigationViewModel.setNavController(navController)
        true
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SettingsDrawer(
                drawerState = drawerState,
            )
        },
    ) {
        HomeScreen(drawerState)
    }
}

@Composable
fun LoginFlow() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    // Set the NavController in the NavigationViewModel
    remember {
        navigationViewModel.setNavController(navController)
        true
    }

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("no_account") {
                NoAccountScreen(
                    onSignup = { navigationViewModel.navigateTo("create_account") },
                    onContinueAsGuest = { authViewModel.continueAsGuest() },
                    onBackToLogin = { navigationViewModel.navigateTo("login") }
                )
            }
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { authViewModel.login() },
                    onCreateAccountClick = { navigationViewModel.navigateTo("no_account") }
                )
            }
            composable("create_account") {
                CreateAccountScreen(
                    onAccountCreated = { authViewModel.login() },
                    onContinueAsGuest = { authViewModel.continueAsGuest() }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RavenGamingNewsTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = {
                SettingsDrawer(
                    drawerState = drawerState,
                )
            },
        ) {
            MainApp()
        }
    }
}
