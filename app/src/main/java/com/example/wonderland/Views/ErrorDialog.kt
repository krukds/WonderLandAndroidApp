package com.example.wonderland.Views

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.ViewModels.UserViewModel

@Composable
fun ErrorDialog(userViewModel: UserViewModel,
                text: String,
                showDialog: MutableState<Boolean>) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Error", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Red) },
            text = { Text(
                text,
                color = Color.Black
            ) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        userViewModel.errorState.value = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4C0099)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("OK", color = Color.White)
                }
            },
            containerColor = Color.White

        )
    }
}