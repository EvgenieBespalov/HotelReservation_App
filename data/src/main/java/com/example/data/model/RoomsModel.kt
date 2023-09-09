package com.example.data.model

import com.google.gson.annotations.SerializedName

data class RoomsModel(
    @SerializedName("rooms")
    val rooms: List<RoomModel>
)

data class RoomModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_per")
    val pricePer: String,
    @SerializedName("peculiarities")
    val imageUrls: List<String>,
    @SerializedName("image_urls")
    val peculiarities: List<String>
)