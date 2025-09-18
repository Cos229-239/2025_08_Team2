package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun AboutScreen(
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
        Spacer(
            modifier = Modifier
            .height(16.dp)
        )
        Text(
            text = "Patch Raven is a project app developed by \nTeam 2 for FullSail PNP2 & 3. " +
                    "The team features \nMel Dommer, Bob Dresner, Frank Gary, \nWesley Filion and Robert Tobiasz. " +
                    "\nPatch Raven is an app concept about providing a news resource that brings all relevant news " +
                    "to the user in a convenient and accessible way. ",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(32.dp, 0.dp, 32.dp, 0.dp)
        )
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    RavenGamingNewsTheme {
        AboutScreen()
    }
}

