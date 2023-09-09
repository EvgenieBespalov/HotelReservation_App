package com.example.domain.entity

data class BookingEntity(
    val id: String,
    val hotelName: String,
    val hotelAdress: String,
    val horating: String,
    val ratingName: String,
    val departure: String,
    val arrivalCountry: String,
    val tourDateStart: String,
    val tourDateStop: String,
    val numberOfNights: String,
    val room: String,
    val nutrition: String,
    val tourPrice: String,
    val fuelCharge: String,
    val serviceCharge: String,
)
