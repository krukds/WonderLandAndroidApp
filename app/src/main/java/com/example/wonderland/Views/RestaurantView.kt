package com.example.wonderland.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.Models.RestaurantModel
import com.example.wonderland.R
import com.example.wonderland.ViewModels.RestaurantViewModel

@Composable
fun RestaurantView(
    restaurantViewModel: RestaurantViewModel,
    navigateToDetailRestaurant: (Int) -> Unit
) {
    println(restaurantViewModel.restaurantsState.value)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF4C0099), Color.Magenta),
                        center = Offset(0f, 0.5f),
                        radius = 2000f
                    )
                )
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Your Food Hub",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(restaurantViewModel.restaurantsState.value.loading) {
                CircularProgressIndicator(color = Color.Black)
            } else if(restaurantViewModel.restaurantsState.value.error != null){
                androidx.compose.material.Text("ERROR OCCURED")
            } else {
                restaurantViewModel.restaurantsState.value.list.forEach {
                    RestaurantItem(restaurant = it, navigateToDetailRestaurant)
                }
            }
        }

    }
}

@Composable
fun RestaurantItem(restaurant: RestaurantModel,
                   navigateToDetailRestaurant: (Int) -> Unit) {
    Button(
        onClick = { navigateToDetailRestaurant(restaurant.id) },
        shape = RoundedCornerShape(0.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.9f)) {
                Row() {
                    RoundedCroppedImage(restaurant.main_photo)
                    Column(
                        modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = restaurant.name,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Row(
                            modifier = Modifier
                                .background(Color(0xFFEBEBEB), shape = RoundedCornerShape(8.dp))
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = restaurant.open_at.toString() + " - " + restaurant.close_at.toString(),
                                color = Color.Black,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.weight(0.1f),
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewFoodView() {
    RestaurantView(RestaurantViewModel(), {})
}