package com.example.hotelreservation_app.presentation

import com.example.domain.entity.RoomEnity


sealed interface RoomScreenUiState{
    object Initial : RoomScreenUiState
    object Loading : RoomScreenUiState
    data class Content(val roomsData: List<RoomEnity>) : RoomScreenUiState
    data class Error(val message: String?) : RoomScreenUiState
}