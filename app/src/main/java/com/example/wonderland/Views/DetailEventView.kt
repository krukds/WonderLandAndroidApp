package com.example.wonderland.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wonderland.Models.AttractionDetailResponse
import com.example.wonderland.Models.EventDetailResponseModel
import com.example.wonderland.R
import com.example.wonderland.ViewModels.EventTicketViewModel
import com.example.wonderland.ViewModels.EventViewModel
import com.example.wonderland.ViewModels.UserViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailEventView(eventId: Int,
                    userViewModel: UserViewModel,
                    eventViewModel: EventViewModel,
                    eventTicketViewModel: EventTicketViewModel,
                    onBackNavCLicked: () -> Unit,
                    navigateToAuth: () -> Unit) {
    var event by remember { mutableStateOf<EventDetailResponseModel?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    println(eventId)
    LaunchedEffect(eventId) {
        isLoading = true
        event = eventViewModel.getEventById(eventId)
        isLoading = false
        println("kejhsmgfuewaihsfkiejsdfiekshf")

    }
    println("1010101101011001010101010101011010101011011010")
    println(event)
    println(event?.start_at)
    val date: LocalDate? = event?.let { parseDateTimeForEvent(it.start_at).toLocalDate() }
    val startTime: LocalTime? = event?.let { parseDateTimeForEvent(it.start_at).toLocalTime() }
    val endTime: LocalTime? = event?.let { parseDateTimeForEvent(it.end_at).toLocalTime() }
    if (isLoading) {
        CircularProgressIndicator(color = Color.Black)
    } else if (event != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .background(Color.Black.copy(alpha = 0.06f))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(event?.photo_url),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
                IconButton(
                    onClick = onBackNavCLicked,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 280.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                        )
                        .fillMaxSize()
                        .drawWithContent {
                            drawContent()
                            drawBottomGradientShadow()
                        }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (event != null) {
                        Text(
                            text = event!!.name,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFEBEBEB), shape = RoundedCornerShape(8.dp))
                            .padding(5.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Date: $date",
                            color = Color.Black
                        )
                        Text(
                            text = "Time: $startTime - $endTime",
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 20.dp, horizontal = 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                event?.let {
                    androidx.compose.material.Text(
                        it.description
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 14.dp, horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material.Text(
                        "$" + event?.price.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    androidx.compose.material.Text(
                        "per Guest",
                        modifier = Modifier.padding(8.dp, end = 16.dp)
                    )
                }
                ButtonBuyTicket(
                    2,
                    eventId,
                    userViewModel,
                    eventTicketViewModel = eventTicketViewModel,
                    navigateToAuth = navigateToAuth
                )

            }
            DetailInfoRow(R.drawable.baseline_location_on_24, "Location", event!!.location)
        }
    }
}

@Preview
@Composable
fun PreviewDetailEventView() {
    DetailEventView(3, UserViewModel(), EventViewModel(), EventTicketViewModel(), {}, {})
}

fun parseDateTimeForEvent(dateTime: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return LocalDateTime.parse(dateTime, formatter)
}

