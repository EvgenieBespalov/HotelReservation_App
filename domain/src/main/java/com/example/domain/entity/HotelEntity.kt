package com.example.domain.entity

data class HotelEntity(
    val id: String,
    val name: String,
    val adress: String,
    val minimalPrice: String,
    val priceForIt: String,
    val rating: String,
    val ratingName: String,
    val imageUrls: List<String>,
    val description: String,
    val peculiarities: List<String>
)
