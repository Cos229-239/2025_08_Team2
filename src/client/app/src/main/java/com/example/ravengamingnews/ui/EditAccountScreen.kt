package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.ui.components.ButtonPR
import com.example.ravengamingnews.ui.components.LogoImagePR
import com.example.ravengamingnews.ui.components.OutlinedTextFieldPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun EditAccountScreen() {
    var isEditing by remember{ mutableStateOf(false)}

    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var fName by remember { mutableStateOf(value = "") }
    var lName by remember { mutableStateOf(value = "") }
    var dateOfBirth by remember { mutableStateOf(value = "")}


    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("EMAIL ADDRESS")},
                    enabled = isEditing
                )
            }
            item {
                OutlinedTextField(
                    value = fName,
                    onValueChange = { fName = it },
                    label = {Text("FIRST NAME")},
                    enabled = isEditing
                )
            }

            item {
                OutlinedTextField(
                    value = lName,
                    onValueChange = { lName = it },
                    label = {Text("LAST NAME")},
                    enabled = isEditing
                )
            }
            item {
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = {},
                    label = {Text("DATE OF BIRTH")},
                    enabled = false
                )
            }
        }
        Button(
            onClick = { isEditing = !isEditing },
            modifier = Modifier
                .align(Alignment.BottomEnd),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp,
                hoveredElevation = 6.dp,
                focusedElevation = 6.dp
            )
        ){
            Text(if (isEditing) "Save" else "Edit")
        }
    }
}

@Preview
@Composable
fun EditAccountScreenPreview() {
    RavenGamingNewsTheme {
        EditAccountScreen()
    }
}
