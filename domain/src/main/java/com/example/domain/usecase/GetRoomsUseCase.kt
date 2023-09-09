package com.example.domain.usecase

import com.example.domain.entity.RoomEnity
import com.example.domain.repository.RoomsRepository

class GetRoomsUseCase(
    private val repository: RoomsRepository
) {
    suspend operator fun invoke(): List<RoomEnity> = repository.getRooms()
}