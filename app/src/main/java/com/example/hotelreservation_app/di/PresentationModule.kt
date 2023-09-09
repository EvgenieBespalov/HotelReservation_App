package com.example.hotelreservation_app.di

import com.example.hotelreservation_app.presentation.BookingScreenViewModel
import com.example.hotelreservation_app.presentation.HotelScreenViewModel
import com.example.hotelreservation_app.presentation.RoomScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun providePresentationModule(): Module =
    module {
        viewModel {
            HotelScreenViewModel(
                getHotelUseCase = get()
            )
        }
        viewModel {
            RoomScreenViewModel(
                getRoomsUseCase = get(),
            )
        }

        viewModel {
            BookingScreenViewModel(
                getBookingUseCase = get()
            )
        }
    }