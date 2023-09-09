package com.example.hotelreservation_app.presentation

import com.example.domain.entity.HotelEntity

sealed interface HotelScreenUiState{
    object Initial : HotelScreenUiState
    object Loading : HotelScreenUiState
    data class Content(val hotelData: HotelEntity) : HotelScreenUiState
    data class Error(val message: String?) : HotelScreenUiState
}