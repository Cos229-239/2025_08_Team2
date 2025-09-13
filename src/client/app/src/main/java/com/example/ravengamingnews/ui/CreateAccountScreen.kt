package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.OutlinedTextFieldPR
import com.example.ravengamingnews.ui.components.TextOnlyButtonPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateAccountViewModel = hiltViewModel(),
    onAccountCreated: () -> Unit = {},
    onContinueAsGuest: () -> Unit = {},
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val dateOfBirth by viewModel.dateOfBirth.collectAsState()

    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
    val firstNameError by viewModel.firstNameError.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()
    val dateOfBirthError by viewModel.dateOfBirthError.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val signUpErrorMessage by viewModel.signUpErrorMessage.collectAsState()
    val isSignUpSuccess by viewModel.isSignUpSuccess.collectAsState()

    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LogoImagePR(
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        if (signUpErrorMessage.isNotEmpty()) {
            item {
                Text(
                    text = signUpErrorMessage,
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        item {
            OutlinedTextFieldPR(
                label = stringResource(R.string.email_address),
                value = email,
                isError = emailError != null,
                errorMessage = emailError,
                onValueChanged = { viewModel.onEmailChange(it) },
                onKeyboardAction = {}
            )
        }

        item {
            OutlinedTextFieldPR(
                label = stringResource(R.string.password),
                value = password,
                isError = passwordError != null,
                errorMessage = passwordError,
                onValueChanged = { viewModel.onPasswordChange(it) },
                onKeyboardAction = {},
                isPassword = true
            )
        }

        item {
            OutlinedTextFieldPR(
                label = stringResource(R.string.confirm_password),
                value = confirmPassword,
                isError = confirmPasswordError != null,
                errorMessage = confirmPasswordError,
                onValueChanged = { viewModel.onConfirmPasswordChange(it) },
                onKeyboardAction = {},
                isPassword = true
            )
        }

        item {
            OutlinedTextFieldPR(
                label = stringResource(R.string.first_name),
                value = firstName,
                isError = firstNameError != null,
                errorMessage = firstNameError,
                onValueChanged = { viewModel.onFirstNameChange(it) },
                onKeyboardAction = {}
            )
        }

        item {
            OutlinedTextFieldPR(
                label = stringResource(R.string.last_name),
                value = lastName,
                isError = lastNameError != null,
                errorMessage = lastNameError,
                onValueChanged = { viewModel.onLastNameChange(it) },
                onKeyboardAction = {}
            )
        }

        item {
            OutlinedTextFieldPR(
                label = stringResource(R.string.date_of_birth),
                value = dateOfBirth,
                isError = dateOfBirthError != null,
                errorMessage = dateOfBirthError,
                onValueChanged = { viewModel.onDateOfBirthChange(it) },
                onKeyboardAction = {}
            )
        }

        item {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                ButtonPR(
                    modifier = Modifier,
                    text = stringResource(R.string.create_account),
                    onClick = {
                        viewModel.onCreateAccount()
                    },
                    fontSize = 16.sp
                )
            }
        }

        item {
            TextOnlyButtonPR(
                text = stringResource(R.string.continue_without_account),
                onClick = onContinueAsGuest
            )
        }
    }

    // Observe sign-up message changes to trigger navigation
    androidx.compose.runtime.LaunchedEffect(signUpErrorMessage) {
        if (isSignUpSuccess) {
            onAccountCreated()
        }
    }
}

@Preview
@Composable
fun CreateAccountScreenPreview() {
    RavenGamingNewsTheme {
        CreateAccountScreen()
    }
}
