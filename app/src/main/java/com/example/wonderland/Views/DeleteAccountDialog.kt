package com.example.wonderland.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeleteAccountDialog(showDialog: MutableState<Boolean>,
                        onDeleteClicked: () -> Unit) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier.background(Color.White, RoundedCornerShape(16.dp)),
            title = { Text("Confirmation", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            text = { Text("Are you sure you want to delete your account? You will lose all your data, tickets and reservations.",
                color = Color.Black) },
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth(), // Заповнити максимальну ширину для горизонтального розташування
                    horizontalArrangement = Arrangement.SpaceBetween // Розподілити простір рівномірно
                ) {
                    Button(
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4C0099)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                    Spacer(Modifier.width(16.dp)) // Відстань між кнопками
                    Button(
                        onClick = {
                            onDeleteClicked()
                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4C0099)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Confirm", color = Color.White)
                    }
                }
            }
        )
    }
}