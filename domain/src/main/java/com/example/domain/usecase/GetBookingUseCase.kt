package com.example.domain.usecase

import com.example.domain.entity.BookingEntity
import com.example.domain.repository.BookingRepository

class GetBookingUseCase(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(): BookingEntity = repository.getBooking()
}