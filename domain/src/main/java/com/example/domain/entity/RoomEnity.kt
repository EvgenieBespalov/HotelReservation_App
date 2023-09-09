package com.example.domain.entity

data class RoomEnity(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val imageUrls: List<String>,
    val peculiarities: List<String>
)