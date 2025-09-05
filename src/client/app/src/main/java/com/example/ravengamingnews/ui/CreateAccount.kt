package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.OutlinedTextFieldPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun AccountCreationScreen(
modifier: Modifier = Modifier
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoImagePR(
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
            )
            OutlinedTextFieldPR(
                label = "EMAIL ADDRESS",
                value = "test@@test.com",
                isError = true,
                errorMessage = stringResource(R.string.error_invalid_email),
                onValueChanged = {},
                onKeyboardAction = {}
            )
            OutlinedTextFieldPR(
                label = stringResource(R.string.password),
                value = "password",
                isError = false,
                onValueChanged = {},
                onKeyboardAction = {},
                isPassword = true
            )
            OutlinedTextFieldPR(
                label = "CONFIRM PASSWORD",
                value = "password",
                isError = false,
                onValueChanged = {},
                onKeyboardAction = {},
                isPassword = true
            )
            OutlinedTextFieldPR(
                label = "FIRST NAME",
                value = "JOHN",
                onValueChanged = {},
                onKeyboardAction = {}
            )
            OutlinedTextFieldPR(
                label = "LAST NAME",
                value = "DOE",
                onValueChanged = {},
                onKeyboardAction = {}
            )
            OutlinedTextFieldPR(
                label = "DATE OF BIRTH",
                value = "01/01/1960",
                onValueChanged = {},
                onKeyboardAction = {}
            )
        }
    }
}

@Preview
@Composable
fun AccountCreationPreview(){
    RavenGamingNewsTheme {
        AccountCreationScreen()
    }
}