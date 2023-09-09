package com.example.hotelreservation_app.presentation

import com.example.domain.entity.BookingEntity

sealed interface BookingScreenUiState{
    object Initial : BookingScreenUiState
    object Loading : BookingScreenUiState
    data class Content(val bookingData: BookingEntity) : BookingScreenUiState
    data class Error(val message: String?) : BookingScreenUiState
}