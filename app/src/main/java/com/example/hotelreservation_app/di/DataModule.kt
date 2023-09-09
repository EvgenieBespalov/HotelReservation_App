package com.example.hotelreservation_app.di

import com.example.data.converter.BookingConverter
import com.example.data.converter.HotelConverter
import com.example.data.converter.RoomConverter
import org.koin.core.module.Module
import org.koin.dsl.module

fun provideDataModule(): Module =
    module {
        factory { HotelConverter() }
        factory { RoomConverter() }
        factory { BookingConverter() }
    }