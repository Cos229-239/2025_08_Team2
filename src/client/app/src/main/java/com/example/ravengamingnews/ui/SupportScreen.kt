package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun SupportScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoImagePR(
            modifier = Modifier
                .size(250.dp)
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        )
        Text(
            text = "This app is currently in development. Please call 0118 999 881 999 119 725 3.",
            modifier = Modifier
                .padding(32.dp, 0.dp, 32.dp, 0.dp)
        )
    }
}

@Preview
@Composable
fun SupportScreenPreview() {
    RavenGamingNewsTheme {
        SupportScreen()
    }
}
