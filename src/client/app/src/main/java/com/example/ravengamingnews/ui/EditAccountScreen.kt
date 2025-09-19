package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.ui.components.OutlinedTextFieldPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun EditAccountScreen(
    viewModel: EditAccountViewModel = hiltViewModel()
) {
    val isEditing = viewModel.editing.collectAsState().value
    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val fistNameError by viewModel.firstNameError.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()
    val dateOfBirth by viewModel.dateOfBirth.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextFieldPR(
                    value = email,
                    onValueChanged = { viewModel.onEmailChange(it) },
                    label = "EMAIL ADDRESS",
                    onKeyboardAction = {},
                    isError = emailError != null,
                    errorMessage = emailError,
                    isEditable = isEditing,
                    isEnabled = true
                )
            }
            item {
                OutlinedTextFieldPR(
                    value = firstName,
                    onValueChanged = { viewModel.onFirstNameChange(it) },
                    label = "FIRST NAME",
                    onKeyboardAction = {},
                    isError = fistNameError != null,
                    errorMessage = fistNameError,
                    isEditable = isEditing,
                    isEnabled = true
                )
            }

            item {
                OutlinedTextFieldPR(
                    value = lastName,
                    onValueChanged = { viewModel.onLastNameChange(it) },
                    label = "LAST NAME",
                    onKeyboardAction = {},
                    isError = lastNameError != null,
                    errorMessage = lastNameError,
                    isEditable = isEditing,
                    isEnabled = true
                )
            }
            item {
                OutlinedTextFieldPR(
                    value = dateOfBirth,
                    onValueChanged = {},
                    label = "DATE OF BIRTH",
                    onKeyboardAction = {},
                    isEditable = false,
                    isEnabled = false
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Button(
                    onClick = {
                        if (isEditing) {
                            viewModel.onSaveChanges()
                        } else {
                            viewModel.setEditing(true)
                        }
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp,
                        hoveredElevation = 6.dp,
                        focusedElevation = 6.dp
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(75.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        if (isEditing) Icons.Filled.Check else Icons.Filled.Edit,
                        "Edit button",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
                if (isEditing) Button(
                    onClick = { viewModel.setEditing(false) },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp,
                        hoveredElevation = 6.dp,
                        focusedElevation = 6.dp
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(75.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        Icons.Filled.Close,
                        "Cancel Button",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }
}

@Preview
@Composable
fun EditAccountScreenPreview() {
    RavenGamingNewsTheme {
        EditAccountScreen()
    }
}
