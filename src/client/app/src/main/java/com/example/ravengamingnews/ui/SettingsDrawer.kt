package com.example.ravengamingnews.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.R
import com.example.ravengamingnews.navigation.AppRoutes
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    settingsDrawerViewModel: SettingsDrawerViewModel = hiltViewModel(),
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
                drawerState = drawerState,
                modifier = modifier,
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomDrawerSection(
                drawerState = drawerState,
                modifier = modifier,
                settingsDrawerViewModel = settingsDrawerViewModel
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
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
) {
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current  // Add this line to get the context
    Column(
        modifier = modifier.padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
    ) {
        SettingsButton(
            text = stringResource(R.string.account),
            onClick = {
                scope.launch { drawerState.close() }
                navigationViewModel.navigateTo(AppRoutes.SETTINGS_EDIT_ACCOUNT)
            }
        )
        SettingsButton(
            text = stringResource(R.string.filters),
            onClick = {
                scope.launch { drawerState.close() }
                navigationViewModel.navigateTo(AppRoutes.SETTINGS_FILTERS)
            }
        )
        SettingsButton(
            text = stringResource(R.string.notifications),
            onClick = {
                scope.launch { drawerState.close() }
                // Open Android App Notifications Settings for this app
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            }
        )
        SettingsButton(
            text = stringResource(R.string.saved),
            onClick = {
                scope.launch { drawerState.close() }
                navigationViewModel.navigateTo(AppRoutes.SETTINGS_SAVED)
            }
        )
    }
}

@Composable
private fun BottomDrawerSection(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    settingsDrawerViewModel: SettingsDrawerViewModel
) {
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.padding(start = 16.dp, top = 2.dp, bottom = 32.dp)
    ) {
        SettingsButton(
            text = stringResource(R.string.support).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            onClick = {
                scope.launch { drawerState.close() }
                navigationViewModel.navigateTo(AppRoutes.SETTINGS_SUPPORT)
            }
        )
        SettingsButton(
            text = stringResource(R.string.about).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            onClick = {
                scope.launch { drawerState.close() }
                navigationViewModel.navigateTo(AppRoutes.SETTINGS_ABOUT)
            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        val buttonText =
            if (settingsDrawerViewModel.isSignedIn()) stringResource(
                R.string.sign_out
            ) else stringResource(R.string.login)
        ButtonPR(
            text = buttonText,
            onClick = {
                scope.launch { drawerState.close() }
                settingsDrawerViewModel.signOut()
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
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = {
                SettingsDrawer(
                    drawerState = drawerState,
                )
            },
        ) {
        }
    }
}
