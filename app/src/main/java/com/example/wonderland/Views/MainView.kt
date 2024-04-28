package com.example.wonderland.Views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.Models.AttractionModel
import com.example.wonderland.ViewModels.AttractionViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.wonderland.Models.EventModel
import com.example.wonderland.R
import com.example.wonderland.ViewModels.EventViewModel
import com.example.wonderland.ViewModels.MainTabsViewModel
import java.time.LocalDate
import java.time.LocalTime


@Composable
fun MainView(attractionViewModel: AttractionViewModel,
             eventViewModel: EventViewModel,
             mainTabsViewModel: MainTabsViewModel,
             navigateToFilter: () -> Unit,
             navigateToDetailAttraction: (Int) -> Unit,
             navigateToDetailEvent: (Int) -> Unit) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(mainTabsViewModel.tabsState.value) }
    val screenWidthDp = LocalConfiguration.current.screenWidthDp

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTab(
            items = listOf("Attractions", "Events"),
            selectedItemIndex = selectedTab,
            mainTabsViewModel = mainTabsViewModel,
            onClick = setSelectedTab,
            tabWidth = screenWidthDp.dp / 2 - 20.dp
        )

        ContentForMainTabs(
            selectedTab,
            attractionViewModel,
            eventViewModel,
            navigateToFilter,
            navigateToDetailAttraction,
            navigateToDetailEvent
        )

    }

}

@Composable
fun ContentForMainTabs(selectedIndex: Int,
                       attractionViewModel: AttractionViewModel,
                       eventViewModel: EventViewModel,
                       navigateToFilter: () -> Unit,
                       navigateToDetailAttraction: (Int) -> Unit,
                       navigateToDetailEvent: (Int) -> Unit) {

    when (selectedIndex) {
        0 -> {
            Scaffold(
                floatingActionButton = {
                    val filterCount = attractionViewModel.selectedTags.count { it == true } + attractionViewModel.selectedAges.count { it == true }
                    FloatingActionButton(
                        onClick = { navigateToFilter() },
                        backgroundColor = Color(0xFF4C0099),
                        contentColor = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painterResource(id = R.drawable.filter),
                                tint = Color.White,
                                contentDescription = "Filter",
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(4.dp)
                            )
                            // Показуємо значок тільки якщо є активні фільтри
                            if (filterCount > 0) {
                                Badge(
                                    backgroundColor = Color.Red, // Змінено для кращої видимості
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(0.dp)
                                ) {
                                    Text(text = "$filterCount", color = Color.White, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                },
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 8.dp)
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(attractionViewModel.attractionsState.value.loading) {
                        CircularProgressIndicator(color = Color.Black)
                    } else if(attractionViewModel.attractionsState.value.error != null){
                        androidx.compose.material.Text("ERROR OCCURED")
                    } else {
                        attractionViewModel.attractionsState.value.list.forEach {
                            AttractionItem(attraction = it, navigateToDetailAttraction)
                        }
                    }
                }
            }
        }
        1 -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(eventViewModel.eventsState.value.loading) {
                    CircularProgressIndicator(color = Color.Black)
                } else if(eventViewModel.eventsState.value.error != null){
                    androidx.compose.material.Text("ERROR OCCURED")
                } else {
                    eventViewModel.eventsState.value.list.forEach {
                        EventItem(event = it, navigateToDetailEvent = navigateToDetailEvent)
                    }
                }
            }
        }
    }
}

@Composable
fun EventItem(event: EventModel, navigateToDetailEvent: (Int) -> Unit) {
    val date: LocalDate = event.start_at.toLocalDate()
    val startTime: LocalTime = event.start_at.toLocalTime()
    val endTime: LocalTime = event.end_at.toLocalTime()
    Button(
        onClick = { navigateToDetailEvent(event.id) },
        shape = RoundedCornerShape(0.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.9f)) {
                Row() {
                    RoundedCroppedImage(event.photo_url)
                    Column(
                        modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = event.name,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
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

@Composable
fun AttractionItem(attraction: AttractionModel, navigateToDetailAttraction: (Int) -> Unit) {
    Button(
        onClick = { navigateToDetailAttraction(attraction.id) },
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
                    RoundedCroppedImage(attraction.photo_url)
                    Column(
                        modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = attraction.name,
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

@Composable
fun RoundedCroppedImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    contentScale: ContentScale = ContentScale.Crop,
    backgroundColor: Color = Color.LightGray
) {
    val pxValue = with(LocalDensity.current) { cornerRadius.toPx() }
    Box(
        modifier = modifier
            .height(64.dp)
            .width(64.dp)
            .clip(shape = RoundedCornerShape(cornerRadius))
            .background(Color.White)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                    .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                        transformations(RoundedCornersTransformation(pxValue))
                    }).build()
            ),
            contentDescription = imageUrl,
            contentScale = contentScale,
            modifier = Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(cornerRadius)
                }
        )
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewMainView() {
    MainView(AttractionViewModel(), EventViewModel(), MainTabsViewModel(), {}, {}, {})
}