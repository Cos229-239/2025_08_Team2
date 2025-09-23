package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.R
import com.example.ravengamingnews.data.Filter
import com.example.ravengamingnews.data.GameFilter
import com.example.ravengamingnews.data.TopicFilter
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun FiltersScreenAlt(
    modifier: Modifier = Modifier,
    viewModel: FiltersViewModel = hiltViewModel(),
) {
    val gameSwitches = viewModel.gameFilters.collectAsState().value
    val topicSwitches = viewModel.topicFilters.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val isSaving = viewModel.isSaving.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val hasUnsavedChanges = viewModel.hasUnsavedChanges.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading || isSaving) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }
        if (errorMessage != null) {
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = errorMessage)
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
        ) {
            item {
                FilterSwitchGroup(
                    title = "Games",
                    switches = gameSwitches,
                    modifier = Modifier.padding(bottom = 16.dp),
                    viewModel = viewModel
                )
            }
            item { HorizontalDivider(modifier = modifier.padding(bottom = 16.dp)) }
            item {
                FilterSwitchGroup(
                    title = "Topics",
                    switches = topicSwitches,
                    modifier = Modifier.padding(bottom = 16.dp),
                    viewModel = viewModel
                )
            }
            item { Spacer(modifier = Modifier.size(100.dp)) }  // To provide space for the floating button}
        }
        if (hasUnsavedChanges) {
            Button(
                onClick = { viewModel.saveUserFilters() },
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp,
                    hoveredElevation = 6.dp,
                    focusedElevation = 6.dp
                ),
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(75.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(R.string.save),
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUserFilters()
    }
}

@Composable
private fun FilterSwitchGroup(
    title: String,
    switches: List<Filter>,
    modifier: Modifier = Modifier,
    viewModel: FiltersViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier.padding(bottom = 4.dp)
        )
        switches.forEach { switchData ->
            FilterSwitchRow(
                isChecked = switchData.isChecked,
                onCheckedChange = {
                    when (switchData) {
                        is GameFilter -> viewModel.toggleGameFilter(switchData.gameId)
                        is TopicFilter -> viewModel.toggleTopicFilter(switchData.topicEnum)
                    }
                },
                switchData = switchData,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun FilterSwitchRow(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    switchData: Filter,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(switchData.titleResId),
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun FilterSwitchRowPreview() {
    RavenGamingNewsTheme {
        FiltersScreenAlt()
    }
}
