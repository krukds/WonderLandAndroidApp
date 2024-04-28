package com.example.wonderland.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderland.Models.AgeModel
import com.example.wonderland.Models.AttractionDetailResponse
import com.example.wonderland.Models.AttractionModel
import com.example.wonderland.Models.TagModel
import com.example.wonderland.ageService
import com.example.wonderland.attractionsService
import com.example.wonderland.tagService
import kotlinx.coroutines.launch

class AttractionViewModel: ViewModel() {
    private val _attractionsState = mutableStateOf(AttractionsState())
    val attractionsState: State<AttractionsState> = _attractionsState

    private val _tagsState = mutableStateOf(emptyList<TagModel>())
    val tagsState = _tagsState

    private val _agesState = mutableStateOf(emptyList<AgeModel>())
    val agesState = _agesState

    var selectedTags = mutableStateListOf<Boolean>()
    var selectedAges = mutableStateListOf<Boolean>()

    init {
        fetchTags()
        fetchAges()
        fetchAttractionsFiltered()
    }

    private fun updateSelectedState() {
        selectedTags.clear()
        selectedTags.addAll(List(tagsState.value.size) { false })
        selectedAges.clear()
        selectedAges.addAll(List(agesState.value.size) { false })
    }

    fun fetchAttractionsFiltered(tagNames: String? = "", ageNames: String? = "") {
        viewModelScope.launch {
            try {
                val response = attractionsService.getAllAttractions(tagNames, ageNames)
                _attractionsState.value = _attractionsState.value.copy(
                    loading = false,
                    list = response,
                    error = null
                )
            } catch (e: Exception) {
                _attractionsState.value = _attractionsState.value.copy(
                    loading = false,
                    error = "Error fetching filtered attractions: ${e.message}"
                )
            }
        }
    }

    suspend fun getAttractionById(id: Int): AttractionDetailResponse {
        return attractionsService.getAttractionById(id)
    }

    fun fetchTags() {
        viewModelScope.launch {
            try {
                val response = tagService.getAllTags()
                _tagsState.value = response
                updateSelectedState()
            } catch (_:Exception) {
            }
        }
    }

    fun fetchAges() {
        viewModelScope.launch {
            try {
                val response = ageService.getAllAges()
                _agesState.value = response
                updateSelectedState()
            } catch (_:Exception) {
            }
        }
    }

    data class AttractionsState(
        var loading: Boolean = true,
        val list: List<AttractionModel> = emptyList(),
        val error: String? = null
    )
}