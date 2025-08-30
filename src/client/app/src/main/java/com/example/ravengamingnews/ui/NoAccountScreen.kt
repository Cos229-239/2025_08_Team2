package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.components.TextOnlyButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun NoAccountScreen(
    modifier: Modifier = Modifier,
    onAccountCreated: () -> Unit = {},
    onContinueAsGuest: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.create_account),
        )
        ButtonPR(
            text = stringResource(R.string.create_account),
            onClick = onAccountCreated, // TODO: Needs to actually route to the create account screen
            modifier = modifier
        )
        TextOnlyButtonPR(
            text = stringResource(R.string.continue_without_account),
            onClick = onContinueAsGuest,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun NoAccountScreenPreview() {
    RavenGamingNewsTheme {
        NoAccountScreen()
    }
}
