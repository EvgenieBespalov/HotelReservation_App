package com.example.domain.repository

import com.example.domain.entity.RoomEnity

interface RoomsRepository {
    suspend fun getRooms(): List<RoomEnity>
}