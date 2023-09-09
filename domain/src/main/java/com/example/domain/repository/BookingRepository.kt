package com.example.domain.repository

import com.example.domain.entity.BookingEntity

interface BookingRepository {
    suspend fun getBooking(): BookingEntity
}