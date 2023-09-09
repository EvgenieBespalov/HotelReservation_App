package com.example.data.converter

import com.example.data.model.HotelModel
import com.example.domain.entity.HotelEntity

class HotelConverter {
    fun convertModelInEntity(from: HotelModel): HotelEntity =
        HotelEntity(
            id = from.id.toString(),
            name = from.name,
            adress = from.adress,
            minimalPrice = from.minimalPrice.toString(),
            priceForIt = from.priceForIt,
            rating = from.rating.toString(),
            ratingName = from.ratingName,
            imageUrls = from.imageUrls,
            description = from.aboutTheHotel.description,
            peculiarities = from.aboutTheHotel.peculiarities,
        )
}