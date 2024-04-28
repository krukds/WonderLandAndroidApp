package com.example.wonderland.Views

import PreferencesManager
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wonderland.Models.TicketModel
import com.example.wonderland.R
import com.example.wonderland.ViewModels.AttractionTicketViewModel
import com.example.wonderland.ViewModels.EventTicketViewModel
import com.example.wonderland.ViewModels.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun TicketsView(attractionTicketViewModel: AttractionTicketViewModel,
                eventTicketViewModel: EventTicketViewModel,
                userViewModel: UserViewModel,
                onBackNavCLicked: () -> Unit,
                ticketType: Int) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(0) }
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    var tickets: List<TicketModel> = emptyList()

    val sort = remember { mutableStateOf(-1)}

    if(ticketType == 1 && tickets.isEmpty()) {
        attractionTicketViewModel.fetchTickets(PreferencesManager(LocalContext.current).getData("token", ""), sort.value)
    } else if(ticketType == 2 && tickets.isEmpty()) {
        eventTicketViewModel.fetchTickets(PreferencesManager(LocalContext.current).getData("token", ""), sort.value)
    }

    tickets = if(ticketType == 1) {
        attractionTicketViewModel.ticketsState.value.list
    } else {
        eventTicketViewModel.ticketsState.value.list
    }

    var activeTickets = tickets.filter { ticket ->
        !ticket.is_expired
    }
    val expiredTickets = tickets.filter { ticket ->
        ticket.is_expired
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    sort.value *= -1
                },
                backgroundColor = Color(0xFF4C0099),
                contentColor = Color.White
            ) {
                Icon(
                    painterResource(id = R.drawable.sort),
                    tint = Color.White,
                    contentDescription = "Filter",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFFECECEC))
        ) {
            var title = "My attraction tickets"
            if (ticketType == 2)
                title = "My event tickets"
            TopBarView(title, onBackNavCLicked)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFECECEC))
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTab(
                    items = listOf("Active", "Expired"),
                    selectedItemIndex = selectedTab,
                    onClick = setSelectedTab,
                    tabWidth = screenWidthDp.dp / 2 - 20.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (selectedTab) {
                        0 -> {
                            if (!activeTickets.isEmpty()) {
                                activeTickets.forEach {
                                    TicketItem(
                                        ticket = it,
                                        userViewModel.userState.value.first_name + " " + userViewModel.userState.value.last_name
                                    )
                                }
                            } else {
                                Text("You do not have active tickets")
                            }
                        }

                        1 -> {
                            if (!expiredTickets.isEmpty()) {
                                expiredTickets.forEach {
                                    TicketItem(
                                        ticket = it,
                                        userViewModel.userState.value.first_name + " " + userViewModel.userState.value.last_name
                                    )
                                }
                            } else {
                                Text("You do not have expired tickets")
                            }
                        }
                    }
                }
            }
        }
    }



}

fun parseDateTime(dateTime: String): LocalDateTime? {
    return try {
        LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy.MM.dd H:m:s"))
    } catch (e: Exception) {
        null
    }
}


@Composable
fun TicketItem(ticket: TicketModel, user_name: String) {
    Surface(
        elevation = 16.dp,
        modifier = Modifier.padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 26.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = ticket.title,
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        val parsedDateTime = parseDateTime(ticket.created_at)
                        // Форматер для конвертації LocalDateTime в бажаний формат
                        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)

                        // Перетворення LocalDateTime в рядок за допомогою форматера
                        val formattedDateTime = parsedDateTime?.format(formatter) ?: "Invalid Date"

                        // Вивід тексту
                        Text(text = formattedDateTime)
                    }
                    Text(user_name)

                }
            }
            Row(
                modifier = Modifier
                    .background(Color(0xFF4C0099), shape = RoundedCornerShape(bottomEnd = 16.dp))
                    .padding(vertical = 5.dp, horizontal = 16.dp)
            ) {
                Text(
                    ticket.id.toString(),
                    color = Color.White
                )
            }
        }

    }
}


@Preview
@Composable
fun PreviewTicketsView() {
    TicketsView(AttractionTicketViewModel(), EventTicketViewModel(), UserViewModel(), {},1)
}