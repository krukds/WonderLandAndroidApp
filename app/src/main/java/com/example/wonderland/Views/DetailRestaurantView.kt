package com.example.wonderland.Views

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.caloriestracker.Views.ImageCarousel
import com.example.wonderland.Models.AttractionDetailResponse
import com.example.wonderland.Models.RestaurantDetailResponse
import com.example.wonderland.R
import com.example.wonderland.ViewModels.RestaurantViewModel

@Composable
fun DetailRestaurantView(
    restaurantId: Int,
    restaurantViewModel: RestaurantViewModel,
    onBackNavCLicked: () -> Unit
) {
    var restaurant by remember { mutableStateOf<RestaurantDetailResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    println("44444444444444444444")
    println(restaurantId)
    LaunchedEffect(restaurantId) {
        isLoading = true
        restaurant = restaurantViewModel.getRestaurantById(restaurantId)
        isLoading = false
    }
    println(restaurant)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .background(Color.Black.copy(alpha = 0.06f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.Black)
        } else if (restaurant != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(restaurant?.photos?.get(0)),
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
                    Text(text = restaurant!!.name)
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
                            text = restaurant!!.open_at + " - " + restaurant!!.close_at,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
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
                restaurant?.let {
                    Text(
                        it.description
                    )
                }
            }

            DetailInfoRow(R.drawable.baseline_location_on_24, "Location", restaurant!!.location)
            val cuisines = restaurant!!.cuisines.joinToString(separator = ", ")
            DetailInfoRow(R.drawable.cuisine, "Cuisines", cuisines)
            DetailInfoRow(R.drawable.telephone, "Phone", restaurant!!.phone)


            MenuRow(menuUrl = restaurant!!.menu_url)

            ImageCarousel(restaurant!!.photos)
        } else {
            Text("Failed to load attraction details")
        }
    }
}

@Composable
fun MenuRow(menuUrl: String) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 20.dp, horizontal = 40.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Menu",
            fontSize = 18.sp,
            modifier = Modifier.clickable {
                uriHandler.openUri(menuUrl)
            },
            textDecoration = TextDecoration.Underline,
            color = Color(0xFF4C0099)
        )
    }
}

@Preview
@Composable
fun PreviewDetailRestaurantView() {
    DetailRestaurantView(1, RestaurantViewModel(), {})
}
