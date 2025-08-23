package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun EditAccountScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.edit_account),
        )
    }
}

@Preview
@Composable
fun EditAccountScreenPreview() {
    RavenGamingNewsTheme {
        EditAccountScreen()
    }
}
