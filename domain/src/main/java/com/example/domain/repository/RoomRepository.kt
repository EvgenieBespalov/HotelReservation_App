package com.example.domain.repository

import com.example.domain.entity.RoomEnity

interface RoomRepository {
    suspend fun getRooms(): List<RoomEnity>
}