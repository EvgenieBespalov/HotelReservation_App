package com.example.domain.entity

data class RoomEnity(
    val id: String,
    val name: String,
    val price: String,
    val pricePer: String,
    val imageUrls: List<String>,
    val peculiarities: List<String>
)