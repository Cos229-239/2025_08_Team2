package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ravengamingnews.R
import com.example.ravengamingnews.TempNavScreen
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import kotlinx.coroutines.launch

@Composable
fun ModalDrawerSheetPR(
    navController: NavHostController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
)
{
    val scope = rememberCoroutineScope()
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
        drawerShape = RectangleShape,
    )
    {
        Column(
            modifier = modifier.fillMaxWidth(.75f)
        ) {
            Surface(
                shadowElevation = 16.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = modifier
                    .height(64.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = modifier.padding(16.dp)
                )
            }
        }
        Spacer(modifier.height(48.dp))
        Column(
            modifier = modifier.padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
        ) {
            // Account
            TextButton(
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(TempNavScreen.EditAccount.name)
                },
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(R.string.account),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            // Filters
            TextButton(
                onClick = {},
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(R.string.filters),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            // Notifications
            TextButton(
                onClick = {},
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(R.string.notifications),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            // Saved
            TextButton(
                onClick = {},
                modifier = modifier,
            ) {
                Text(
                    text = stringResource(R.string.saved),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.settings),
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    RavenGamingNewsTheme {
        SettingsScreen()
    }
}

@Preview
@Composable
fun ModalDrawerSheetPreview() {
    RavenGamingNewsTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheetPR(
                    navController = navController,
                    drawerState = drawerState,
                )
            },
        ) {
            Scaffold { innerPadding ->
                SettingsScreen(Modifier.padding(innerPadding))
            }
        }
    }
}
