package com.example.ravengamingnews.ui

import android.graphics.drawable.shapes.OvalShape
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.example.ravengamingnews.ui.components.OutlinedTextFieldPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun EditAccountScreen() {
    var isEditing by remember{ mutableStateOf(false)}

    var email by remember { mutableStateOf(value = "") }
   // var password by remember { mutableStateOf(value = "") }
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
                OutlinedTextFieldPR(
                    value = email,
                    onValueChanged = { newText -> email = newText},
                    label = "EMAIL ADDRESS",
                    onKeyboardAction = {},
                    isEditable = isEditing,
                    isEnabled = true
                )
            }
            item {
                OutlinedTextFieldPR(
                    value = fName,
                    onValueChanged = { newText -> fName = newText},
                    label = "FIRST NAME",
                    onKeyboardAction = {},
                    isEditable = isEditing,
                    isEnabled = true
                )
            }

            item {
                OutlinedTextFieldPR(
                    value = lName,
                    onValueChanged = { newText -> lName = newText},
                    label = "LAST NAME",
                    onKeyboardAction = {},
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
                    onClick = { isEditing = !isEditing },
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
                    onClick = { isEditing = false },
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
}

@Preview
@Composable
fun EditAccountScreenPreview() {
    RavenGamingNewsTheme {
        EditAccountScreen()
    }
}
