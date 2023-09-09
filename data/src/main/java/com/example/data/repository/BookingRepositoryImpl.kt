package com.example.data.repository

import com.example.data.api.BookingApi
import com.example.data.converter.BookingConverter
import com.example.domain.entity.BookingEntity
import com.example.domain.repository.BookingRepository

class BookingRepositoryImpl(
    private val api: BookingApi,
    private val converter: BookingConverter
) : BookingRepository{
    override suspend fun getBooking(): BookingEntity = converter.convertNodelInEntity(api.getBooking())
}