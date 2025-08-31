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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SettingsDrawer(
                navController = navController,
                drawerState = drawerState,
            )
        },
    ) {
        HomeScreen(navController, drawerState)
    }
}

@Composable
fun LoginFlow() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { authViewModel.login() },
                    onCreateAccountClick = { navController.navigate("create_account") }
                )
            }
            composable("create_account") {
                NoAccountScreen(
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
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = { SettingsDrawer(
                navController = navController,
                drawerState = drawerState,
            ) },
        ) {
            MainApp()
        }
    }
}
