package com.example.ravengamingnews.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ravengamingnews.AuthViewModel
import com.example.ravengamingnews.HomeScreen
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsDrawer(
    navController: NavHostController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
        drawerShape = RectangleShape,
    ) {
        Column(
            modifier = modifier.fillMaxWidth(.75f)
        ) {
            DrawerHeader()
            MainSettingsDrawerContent(
                navController = navController,
                drawerState = drawerState,
                modifier = modifier,
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomDrawerSection(
                drawerState = drawerState,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun DrawerHeader() {
    // Header styled as TopAppBar, no elevation, with divider at bottom
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(64.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.18f),
                        Color.Transparent
                    )
                )
            )
    )
    Spacer(Modifier.height(48.dp))
}

@Composable
private fun MainSettingsDrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
    ) {
        SettingsButton(
            text = stringResource(R.string.account),
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(HomeScreen.EditAccount.name)
            }
        )
        SettingsButton(
            text = stringResource(R.string.filters),
            onClick = {}
        )
        SettingsButton(
            text = stringResource(R.string.notifications),
            onClick = {}
        )
        SettingsButton(
            text = stringResource(R.string.saved),
            onClick = {}
        )
    }
}

@Composable
private fun BottomDrawerSection(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.padding(start = 16.dp, top = 2.dp, bottom = 32.dp)
    ) {
        SettingsButton(
            text = stringResource(R.string.support).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            onClick = {}
        )
        SettingsButton(
            text = stringResource(R.string.about).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(24.dp))

        val buttonText = if (authState.isLoggedIn) stringResource(R.string.sign_out) else stringResource(R.string.login)
        ButtonPR(
            text = buttonText,
            onClick = {
                scope.launch { drawerState.close() }
                authViewModel.logout()
            },
            modifier = Modifier
                .padding(16.dp, end = 32.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun SettingsButton(
    text: String,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.headlineLarge
) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = style,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun SettingsDrawerPreview() {
    RavenGamingNewsTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = {
                SettingsDrawer(
                    navController = navController,
                    drawerState = drawerState,
                )
            },
        ) {
        }
    }
}
