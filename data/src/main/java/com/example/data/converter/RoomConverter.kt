package com.example.data.converter

import com.example.data.model.RoomModel
import com.example.domain.entity.RoomEnity

class RoomConverter {
    fun convertModelInEntity(from: RoomModel): RoomEnity =
        RoomEnity(
            id = from.id.toString(),
            name = from.name,
            price = from.price.toString(),
            pricePer = from.pricePer,
            imageUrls = from.imageUrls,
            peculiarities = from.peculiarities,
        )
}