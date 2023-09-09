package com.example.hotelreservation_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetBookingUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class BookingScreenViewModel (
    private val getBookingUseCase: GetBookingUseCase
) : ViewModel() {

    private val _state: MutableLiveData<BookingScreenUiState> =
        MutableLiveData(BookingScreenUiState.Initial)
    val state: LiveData<BookingScreenUiState> = _state

    fun initial() {
        viewModelScope.launch {
            _state.value = BookingScreenUiState.Initial
        }
    }

    fun getBookingData() {
        viewModelScope.launch {
            _state.value = BookingScreenUiState.Loading

            try {
                val bookingData = getBookingUseCase()
                _state.value = BookingScreenUiState.Content(bookingData)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = BookingScreenUiState.Error(ex.message)
            }

        }
    }
}