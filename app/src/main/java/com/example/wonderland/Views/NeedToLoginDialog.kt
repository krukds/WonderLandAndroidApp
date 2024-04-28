package com.example.wonderland.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NeedToLoginDialog(text: String,
                      showDialog: MutableState<Boolean>,
                      navigateToLogin:()->Unit) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Error", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Red) },
            text = { Text(
                text,
                color = Color.Black
            ) },
            backgroundColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth(), // Заповнити максимальну ширину для горизонтального розташування
                    horizontalArrangement = Arrangement.SpaceBetween // Розподілити простір рівномірно
                ) {
                    Button(
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cancel", color = Color(0xFF4C0099))
                    }
                    Spacer(Modifier.width(16.dp)) // Відстань між кнопками
                    Button(
                        onClick = {
                            navigateToLogin()
                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4C0099)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Go to authorization", color = Color.White)
                    }
                }
            }
        )
    }
}