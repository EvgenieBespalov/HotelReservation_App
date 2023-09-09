package com.example.data.api

import com.example.data.model.HotelModel
import retrofit2.http.GET

interface HotelApi {
    @GET("/35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    suspend fun getHotel(): HotelModel
}