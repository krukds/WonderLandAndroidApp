package com.example.wonderland.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderland.Models.AttractionDetailResponse
import com.example.wonderland.Models.AttractionModel
import com.example.wonderland.Models.EventModel
import com.example.wonderland.Models.RestaurantDetailResponse
import com.example.wonderland.Models.RestaurantModel
import com.example.wonderland.eventsService
import com.example.wonderland.restaurantsService
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class RestaurantViewModel: ViewModel() {
    private val _restaurantsState = mutableStateOf(RestaurantsState())
    val restaurantsState: State<RestaurantsState> = _restaurantsState

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    init{
        fetchRestaurants()
    }


    private fun fetchRestaurants() {
        viewModelScope.launch {
            try {
                val response = restaurantsService.getAllRestaurants()
                val restaurants = response.map { restaurantJson ->
                    RestaurantModel(
                        id = restaurantJson.id,
                        name = restaurantJson.name,
                        phone = restaurantJson.phone,
                        description = restaurantJson.description,
                        location_id = restaurantJson.location_id,
                        open_at = LocalTime.parse(restaurantJson.open_at, formatter),
                        close_at = LocalTime.parse(restaurantJson.close_at, formatter),
                        menu_url = restaurantJson.menu_url,
                        main_photo = restaurantJson.main_photo
                    )
                }
                _restaurantsState.value = _restaurantsState.value.copy(
                    loading = false,
                    list = restaurants,
                    error = null
                )

            } catch (e: Exception) {
                _restaurantsState.value = _restaurantsState.value.copy(
                    loading = false,
                    error = "Error fetching restaurants ${e.message}"
                )
            }
        }
    }

    suspend fun getRestaurantById(id: Int): RestaurantDetailResponse {
        return restaurantsService.getRestaurantById(id)
    }

    data class RestaurantsState(
        var loading: Boolean = false,
        val list: List<RestaurantModel> = emptyList(),
        val error: String? = null
    )
}