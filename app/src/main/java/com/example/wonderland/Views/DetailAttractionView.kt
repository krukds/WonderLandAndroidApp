package com.example.wonderland.Views

import ButtonWithDialogWithCounter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wonderland.Models.AttractionDetailResponse
import com.example.wonderland.R
import com.example.wonderland.ViewModels.AttractionTicketViewModel
import com.example.wonderland.ViewModels.AttractionViewModel
import com.example.wonderland.ViewModels.EventTicketViewModel
import com.example.wonderland.ViewModels.UserViewModel

@Composable
fun DetailAttractionView(
    attractionId: Int,
    userViewModel: UserViewModel,
    attractionViewModel: AttractionViewModel,
    attractionTicketViewModel: AttractionTicketViewModel,
    onBackNavCLicked: () -> Unit,
    navigateToAuth: () -> Unit) {

    var attraction by remember { mutableStateOf<AttractionDetailResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(attractionId) {
        isLoading = true
        attraction = attractionViewModel.getAttractionById(attractionId)
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black.copy(alpha = 0.06f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.Black)
        } else if (attraction != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(attraction?.photo_url),
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
                    attraction?.let { AttractionTitleDuration(it) }
                }
            }
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 20.dp, horizontal = 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                attraction?.let {
                    Text(
                        it.description
                    )
                }
            }
            Row(modifier = Modifier
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 14.dp, horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("$" + attraction?.price.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text("per Guest", modifier = Modifier.padding(8.dp, end = 16.dp))
                }
                ButtonBuyTicket(1, attractionId, userViewModel = userViewModel, attractionTicketViewModel = attractionTicketViewModel, navigateToAuth = navigateToAuth)

            }
            DetailInfoRow(R.drawable.baseline_location_on_24, "Location", attraction!!.location)
            DetailInfoRow(R.drawable.height, "Minimum height",
                attraction?.let { String.format("%.2f", it.minimum_height) } + " m")
            var tags = attraction!!.tags.joinToString(separator = ", ")
            var ages = attraction!!.ages.joinToString(separator = ", ")
            DetailInfoRow(R.drawable.ferris_wheel, "Interests", tags)
            DetailInfoRow(R.drawable.baseline_emoji_people_24, "Age interests", ages)
        } else {
            Text("Failed to load attraction details")
        }
    }

}

@Composable
fun ButtonBuyTicket(screenId: Int,
                    attractionOrEventId: Int,
                    userViewModel: UserViewModel,
                    attractionTicketViewModel: AttractionTicketViewModel = AttractionTicketViewModel(),
                    eventTicketViewModel: EventTicketViewModel = EventTicketViewModel(),
                    navigateToAuth: () -> Unit
) {
    ButtonWithDialogWithCounter(screenId, attractionOrEventId, userViewModel, attractionTicketViewModel, eventTicketViewModel, navigateToAuth)
}

@Composable
fun DetailInfoRow(iconId: Int, title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(text = title, modifier = Modifier.padding(start = 8.dp))
        }
        Text(text)
    }

}

fun DrawScope.drawBottomGradientShadow() {
    val gradientColor = Brush.verticalGradient(
        colors = listOf(Color.Black.copy(alpha = 0.15f), Color.Black.copy(alpha = 0.06f)),
        startY = size.height - 20f,
        endY = size.height
    )

    drawRect(
        brush = gradientColor,
        topLeft = Offset(0f, size.height - 20f),
        size = androidx.compose.ui.geometry.Size(size.width, 20f)
    )
}

@Composable
fun AttractionTitleDuration(attraction: AttractionDetailResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = attraction.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(Color(0xFFEBEBEB), shape = RoundedCornerShape(8.dp))
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(19.dp)
            )
            Text(
                text = attraction.duration.toString() + " min",
                color = Color.Black,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewDetailAttractionView() {
    DetailAttractionView(1, UserViewModel(), AttractionViewModel(),AttractionTicketViewModel(), {}, {})
}