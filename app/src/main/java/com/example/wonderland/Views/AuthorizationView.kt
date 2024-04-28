package com.example.wonderland.Views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthorizationView(
    navigateToLogin:()-> Unit,
    navigateToSignup:()-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF4C0099), Color.Magenta),
                    center = Offset(0f, 0.5f), // Центр градієнту відносно розміру контейнера
                    radius = 2000f // Радіус градієнту
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            "WonderLand",
            color = Color.White,
            fontSize = 50.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Buy attraction tickets, manage your table bookings, leave reviews, and more!",
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = navigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF4C0099)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Log in", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = navigateToSignup,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            border = BorderStroke(3.dp, Color(0xFF4C0099)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Sign up", fontSize = 18.sp, color = Color(0xFF4C0099))
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun PreviewAuthorizationView() {
    AuthorizationView({}, {})
}