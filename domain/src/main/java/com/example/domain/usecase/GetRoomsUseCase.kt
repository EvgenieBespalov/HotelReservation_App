package com.example.domain.usecase

import com.example.domain.entity.RoomEnity
import com.example.domain.repository.RoomRepository

class GetRoomsUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(): List<RoomEnity> = repository.getRooms()
}