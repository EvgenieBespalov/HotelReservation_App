package com.example.hotelreservation_app.di

import com.example.data.api.BookingApi
import com.example.data.api.HotelApi
import com.example.data.api.RoomsApi
import com.example.data.converter.BookingConverter
import com.example.data.converter.HotelConverter
import com.example.data.converter.RoomConverter
import com.example.data.repository.BookingRepositoryImpl
import com.example.data.repository.HotelRepositoryImpl
import com.example.data.repository.RoomsRepositoryImpl
import com.example.domain.repository.BookingRepository
import com.example.domain.repository.HotelRepository
import com.example.domain.repository.RoomsRepository
import com.example.domain.usecase.GetBookingUseCase
import com.example.domain.usecase.GetHotelUseCase
import com.example.domain.usecase.GetRoomsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

private fun provideHotelRepositoryImpl(
    api: HotelApi,
    converter: HotelConverter
): HotelRepository = HotelRepositoryImpl(api, converter)

private fun provideRoomsRepositoryImpl(
    api: RoomsApi,
    converter: RoomConverter
): RoomsRepository = RoomsRepositoryImpl(api, converter)

private fun provideBookingRepositoryImpl(
    api: BookingApi,
    converter: BookingConverter
): BookingRepository = BookingRepositoryImpl(api, converter)

fun provideDomainModule(): Module =
    module {
        single {
            provideHotelRepositoryImpl(
                api = get(),
                converter = get()
            )
        }
        factory { GetHotelUseCase(repository = get()) }

        single {
            provideRoomsRepositoryImpl(
                api = get(),
                converter = get()
            )
        }
        factory { GetRoomsUseCase(repository = get()) }

        single {
            provideBookingRepositoryImpl(
                api = get(),
                converter = get()
            )
        }
        factory { GetBookingUseCase(repository = get()) }
    }