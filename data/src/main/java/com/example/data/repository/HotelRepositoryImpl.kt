package com.example.data.repository

import com.example.data.api.HotelApi
import com.example.data.converter.HotelConverter
import com.example.domain.entity.HotelEntity
import com.example.domain.repository.HotelRepository

class HotelRepositoryImpl(
    private val api: HotelApi,
    private val converter: HotelConverter
) : HotelRepository{
    override suspend fun getHotel(): HotelEntity = converter.convertModelInEntity(api.getHotel())
}