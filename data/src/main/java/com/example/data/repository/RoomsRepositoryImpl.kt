package com.example.data.repository

import com.example.data.api.RoomsApi
import com.example.data.converter.RoomConverter
import com.example.domain.entity.RoomEnity
import com.example.domain.repository.RoomsRepository

class RoomsRepositoryImpl(
    private val api: RoomsApi,
    private val converter: RoomConverter
) : RoomsRepository{
    override suspend fun getRooms(): List<RoomEnity> = api.getRooms().rooms.map { converter.convertModelInEntity(it) }
}