package com.example.domain.repository

import com.example.domain.entity.HotelEntity

interface HotelRepository {
    suspend fun getHotel(): HotelEntity
}