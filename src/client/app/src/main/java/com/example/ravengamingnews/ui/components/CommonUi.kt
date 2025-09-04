package com.example.ravengamingnews.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.theme.CommonUiSize
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import com.example.ravengamingnews.ui.theme.linkTextStyle

/**
 * Container for common UI components in the app
 * such as buttons, text fields, etc.
 */

/**
 * This is the main text field used in the
 * login and sign up screens in the app.
 *
 * For error handling, pass in isError = true
 * and the errorMessage string to display.
 */
@Composable
fun OutlinedTextFieldPR(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onKeyboardAction: () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions? = null,
    isPassword: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = value,
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.background,
            ),
            onValueChange = onValueChanged,
            label = {
                Text(text = label)
            },
            isError = isError,
            keyboardOptions = keyboardOptions ?: KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { onKeyboardAction() },
                onDone = { onKeyboardAction() }
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = modifier
            )
        }
    }
}

/**
 * This button is the main button for the
 * login, sign up, and log out buttons in the app.
 */
@Composable
fun ButtonPR(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            hoveredElevation = 6.dp,
            focusedElevation = 6.dp
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun TextOnlyButtonPR(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    size: CommonUiSize = CommonUiSize.Medium
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colorScheme.secondary,
            style = linkTextStyle(size)
        )
    }
}

@Composable
fun LogoImagePR(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.patch_raven_logo_ver_3_orange)

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun TopAppBarButtonPR(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (selected) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(32.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun OutlinedTextFieldPreview() {
    RavenGamingNewsTheme {
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
                    label = stringResource(R.string.email),
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
                ButtonPR(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.login),
                    onClick = { }
                )
                TextOnlyButtonPR(
                    text = stringResource(R.string.dont_have_account_sign_up),
                    onClick = { },
                    size = CommonUiSize.Small
                )
                TextOnlyButtonPR(
                    text = stringResource(R.string.dont_have_account_sign_up),
                    onClick = { }
                )
                TextOnlyButtonPR(
                    text = stringResource(R.string.dont_have_account_sign_up),
                    onClick = { },
                    size = CommonUiSize.Large
                )
                Text(
                    text = "Hello World",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Hello World",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Hello World",
                    style = MaterialTheme.typography.headlineLarge
                )
                Row {
                    TopAppBarButtonPR(
                        text = stringResource(R.string.feed),
                        onClick = { }
                    )
                    TopAppBarButtonPR(
                        text = stringResource(R.string.feed),
                        onClick = { },
                        selected = true
                    )
                }
            }
        }
    }
}
