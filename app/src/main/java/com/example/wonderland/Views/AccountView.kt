package com.example.wonderland.Views

import PreferencesManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.R
import com.example.wonderland.ViewModels.UserViewModel

@Composable
fun AccountView(userViewModel: UserViewModel,
                navigateToTickets: (Int) -> Unit,
                navigateToTableBookings: () -> Unit,
                navigateToSettings: () -> Unit,
                navigateToAuthorization: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF4C0099), Color.Magenta),
                        center = Offset(0f, 0.5f), // Центр градієнту відносно розміру контейнера
                        radius = 2000f // Радіус градієнту
                    )
                )
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "WELCOME",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = userViewModel.userState.value.first_name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccountItem(
                iconId = R.drawable.attraction_ticket,
                title = "My Attraction Tickets",
                text = "Find all your attraction tickets here.",
                onNavClicked = { navigateToTickets(1)}
            )
            Spacer(modifier = Modifier.height(16.dp))
            AccountItem(
                iconId = R.drawable.tickets,
                title = "My Event Tickets",
                text = "Find all your event tickets here.",
                onNavClicked = { navigateToTickets(2)}
            )
            Spacer(modifier = Modifier.height(16.dp))
//            AccountItem(
//                iconId = R.drawable.fork_and_knife,
//                title = "My Table Bookings",
//                text = "View, change or cancel your table bookings.",
//                onNavClicked = navigateToTableBookings
//            )
            Spacer(modifier = Modifier.height(26.dp))
            Text("MY PROFILE", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            AccountItem(
                iconId = R.drawable.baseline_settings_24,
                title = "My Account Settings",
                text = "Change your password or your personal information",
                onNavClicked = navigateToSettings
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable {
                        PreferencesManager(context).saveData("token", "")
                        userViewModel.updateUserId(-1)
                        userViewModel.clearToken()
                        navigateToAuthorization()
                    }
                ) {
                    Text(
                        "Log out",
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color(0xFF4C0099),
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painterResource(id = R.drawable.logout),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF4C0099)
                    )
                }
            }
        }
    }
}

@Composable
fun AccountItem(
    iconId: Int,
    title: String,
    text: String,
    onNavClicked: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onNavClicked,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .size(46.dp)
                    .background(Color(0xFFECECEC), shape = CircleShape),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = text,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAccountView() {
    AccountView(
        UserViewModel(),
        {},
        {},
        {},
        {}
    )
}