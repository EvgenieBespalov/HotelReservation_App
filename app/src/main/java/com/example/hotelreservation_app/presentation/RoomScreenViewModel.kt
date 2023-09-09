package com.example.hotelreservation_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetRoomsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class RoomScreenViewModel (
    private val getRoomsUseCase: GetRoomsUseCase,
) : ViewModel() {

    private val _state: MutableLiveData<RoomScreenUiState> =
        MutableLiveData(RoomScreenUiState.Initial)
    val state: LiveData<RoomScreenUiState> = _state

    fun initial() {
        viewModelScope.launch {
            _state.value = RoomScreenUiState.Initial
        }
    }

    fun getRoomsData() {
        viewModelScope.launch {
            _state.value = RoomScreenUiState.Loading

            try {
                val hotelData = getRoomsUseCase()
                _state.value = RoomScreenUiState.Content(hotelData)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = RoomScreenUiState.Error(ex.message)
            }

        }
    }
}