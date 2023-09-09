package com.example.hotelreservation_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetHotelUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class HotelScreenViewModel  (
    private val getHotelUseCase: GetHotelUseCase,
) : ViewModel() {

    private val _state: MutableLiveData<HotelScreenUiState> =
        MutableLiveData(HotelScreenUiState.Initial)
    val state: LiveData<HotelScreenUiState> = _state

    fun initial() {
        viewModelScope.launch {
            _state.value = HotelScreenUiState.Initial
        }
    }

    fun getHotelData() {
        viewModelScope.launch {
            _state.value = HotelScreenUiState.Loading

            try {
                val hotelData = getHotelUseCase()
                _state.value = HotelScreenUiState.Content(hotelData)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = HotelScreenUiState.Error(ex.message)
            }

        }
    }
}