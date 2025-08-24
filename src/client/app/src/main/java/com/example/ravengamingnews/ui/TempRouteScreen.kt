package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

// Temporary screen with button to route to different screens
// like EditAccountScreen, FeedScreen, LoginScreen, SettingsScreen
@Composable
fun TempRouteScreen(
    onFeedClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onEditAccountClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonPR(text = stringResource(R.string.feed), onClick = onFeedClick, modifier = modifier.padding(8.dp))
        ButtonPR(text = stringResource(R.string.login), onClick = onLoginClick, modifier = modifier.padding(8.dp))
        ButtonPR(text = stringResource(R.string.create_account), onClick = onCreateAccountClick, modifier = modifier.padding(8.dp))
        ButtonPR(text = stringResource(R.string.edit_account), onClick = onEditAccountClick, modifier = modifier.padding(8.dp))
        ButtonPR(text = stringResource(R.string.settings), onClick = onSettingsClick, modifier = modifier.padding(8.dp))
    }
}

@Preview
@Composable
fun TempRouteScreenPreview() {
    RavenGamingNewsTheme {
        TempRouteScreen(
            onFeedClick = {},
            onLoginClick = {},
            onCreateAccountClick = {},
            onEditAccountClick = {},
            onSettingsClick = {}
        )
    }
}
