package com.example.ravengamingnews.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import java.util.Locale

/**
 * Container for common UI components in the app
 * such as buttons, text fields, etc.
 */

@Composable
fun TextInputPR(
    label: String,
    text: String,
    modifier: Modifier = Modifier
) {
    // Text(...)
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


@Preview
@Composable
fun PRButtonPreview() {
    RavenGamingNewsTheme {
        ButtonPR(onClick = {}, text = stringResource(R.string.app_name))
    }
}
