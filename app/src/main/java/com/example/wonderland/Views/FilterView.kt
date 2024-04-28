package com.example.wonderland.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.Models.AgeModel
import com.example.wonderland.Models.TagModel
import com.example.wonderland.ViewModels.AttractionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterView(
    attractionViewModel: AttractionViewModel,
    navigateToMain: () -> Unit
) {
    val tags = attractionViewModel.tagsState.value
    val ages = attractionViewModel.agesState.value
    val selectedTags = attractionViewModel.selectedTags
    val selectedAges = attractionViewModel.selectedAges

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Attractions", maxLines = 1) },
                navigationIcon = { IconButton(onClick = navigateToMain) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }},
                actions = {
                    Button(
                        onClick = {
                            selectedTags.fill(false)
                            selectedAges.fill(false)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text("Clear", color = Color(0xFF4C0099))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray)
            )
        },
        floatingActionButton = {
            Button(
                onClick = {
                    attractionViewModel.fetchAttractionsFiltered(
                        tags.filterIndexed { index, _ -> selectedTags[index] }.joinToString(separator = ",") { it.name },
                        ages.filterIndexed { index, _ -> selectedAges[index] }.joinToString(separator = ",") { it.name }
                    )
                    navigateToMain()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4C0099))
            ) {
                Text("Filter", color = Color.White, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (attractionViewModel.tagsState.value.isEmpty() || attractionViewModel.agesState.value.isEmpty()) {
                Text("Loading...")
            } else {
                Text("Tags", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                tags.forEachIndexed { index, tag ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clickable { selectedTags[index] = !selectedTags[index] }
                        .padding(8.dp)
                    ) {
                        Checkbox(checked = selectedTags[index], onCheckedChange = { selectedTags[index] = it })
                        Text(tag.name, modifier = Modifier.padding(start = 8.dp))
                    }
                }

                Text("Ages", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                ages.forEachIndexed { index, age ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clickable { selectedAges[index] = !selectedAges[index] }
                        .padding(8.dp)
                    ) {
                        Checkbox(checked = selectedAges[index], onCheckedChange = { selectedAges[index] = it })
                        Text(age.name, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun f() {
    FilterView(
        AttractionViewModel(),
        {}
    )
}