package com.example.ravengamingnews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.OutlinedTextFieldPR
import com.example.ravengamingnews.ui.components.TextOnlyButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onCreateAccountClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        contentAlignment = Alignment.Center
    ) {
        val email = viewModel.email.collectAsState(initial = "")       // TODO: need to also handle validation error(s) like invalid email format
        val password = viewModel.password.collectAsState(initial = "") // TODO: need to also handle error(s) like cannot be empty
        val message = viewModel.authMessage.collectAsState(initial = "") // TODO: use this to show general auth message (invalid credentials, etc.)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 140.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoImagePR(
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
            )
            OutlinedTextFieldPR(
                value = email.value,
                isError = false,
                onValueChanged = { viewModel.onEmailChange(it) },
                label = stringResource(R.string.email),
                onKeyboardAction = { },
            )
            OutlinedTextFieldPR(
                value = password.value,
                isPassword = true,
                isError = false,
                onValueChanged = { viewModel.onPasswordChange(it) },
                label = stringResource(R.string.password),
                onKeyboardAction = { },
            )
            val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
            ButtonPR(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.login),
                onClick = {
                    localSoftwareKeyboardController?.hide()
                    viewModel.onSignIn()
                }
            )
            TextOnlyButtonPR(
                text = stringResource(R.string.dont_have_account_sign_up),
                onClick = onCreateAccountClick,
                modifier = modifier
            )
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    RavenGamingNewsTheme {
        LoginScreen()
    }
}
