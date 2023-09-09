package com.example.domain.usecase

import com.example.domain.entity.HotelEntity
import com.example.domain.repository.HotelRepository


class GetHotelUseCase (
    private val repository: HotelRepository
) {
    suspend operator fun invoke(): HotelEntity = repository.getHotel()
}