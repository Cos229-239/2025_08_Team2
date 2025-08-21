package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R;

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
        Button(onClick = onFeedClick, modifier = modifier.padding(8.dp)) { Text(text = stringResource(R.string.feed)) }
        Button(onClick = onLoginClick, modifier.padding(8.dp)) { Text(text = stringResource(R.string.login)) }
        Button(onClick = onCreateAccountClick, modifier.padding(8.dp)) { Text(text = stringResource(R.string.create_account)) }
        Button(onClick = onEditAccountClick, modifier.padding(8.dp)) { Text(text = stringResource(R.string.edit_account)) }
        Button(onClick = onSettingsClick, modifier.padding(8.dp)) { Text(text = stringResource(R.string.settings)) }
    }
}

@Preview
@Composable
fun TempRouteScreenPreview() {
    TempRouteScreen(
        onFeedClick = {},
        onLoginClick = {},
        onCreateAccountClick = {},
        onEditAccountClick = {},
        onSettingsClick = {}
    )
}
