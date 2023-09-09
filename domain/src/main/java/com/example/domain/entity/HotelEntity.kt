package com.example.domain.entity

data class HotelEntity(
    val id: Int,
    val name: String,
    val adress: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
    val imageUrls: List<String>,
    val description: String,
    val peculiarities: List<String>
)
