package com.example.data.converter

import com.example.data.model.BookingModel
import com.example.domain.entity.BookingEntity

class BookingConverter {
    fun convertNodelInEntity(from: BookingModel): BookingEntity =
        BookingEntity(
            id = from.id.toString(),
            hotelName = from.hotelName,
            hotelAdress = from.hotelAdress,
            horating = from.horating.toString(),
            ratingName = from.ratingName,
            departure = from.departure,
            arrivalCountry = from.arrivalCountry,
            tourDateStart = from.tourDateStart,
            tourDateStop = from.tourDateStop,
            numberOfNights = from.numberOfNights.toString(),
            room = from.room,
            nutrition = from.nutrition,
            tourPrice = from.tourPrice.toString(),
            fuelCharge = from.fuelCharge.toString(),
            serviceCharge = from.serviceCharge.toString(),
        )
}