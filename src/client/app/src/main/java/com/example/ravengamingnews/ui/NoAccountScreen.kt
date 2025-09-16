package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.TextOnlyButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun NoAccountScreen(
    modifier: Modifier = Modifier,
    onSignup: () -> Unit = {},
    onContinueAsGuest: () -> Unit = {},
    onBackToLogin: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row {
            IconButton(
                onClick = onBackToLogin,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_to_login),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            Text(
                text = stringResource(R.string.back_to_login),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoImagePR(
                modifier = Modifier
                    .widthIn(max = 100.dp)
                    .heightIn(max = 100.dp)
            )
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Column(
            modifier = modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.no_account_prompt),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
            )
            Spacer(Modifier.weight(1f))
            ButtonPR(
                text = stringResource(R.string.sign_up),
                onClick = onSignup,
                modifier = modifier
                    .padding(start = 32.dp, end = 32.dp, bottom = 8.dp)
            )
            TextOnlyButtonPR(
                text = stringResource(R.string.continue_without_account),
                onClick = onContinueAsGuest,
                modifier = modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Preview
@Composable
fun NoAccountScreenPreview() {
    RavenGamingNewsTheme {
        NoAccountScreen()
    }
}
