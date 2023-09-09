package com.example.data.api

import com.example.data.model.BookingModel
import retrofit2.http.GET

interface BookingApi {
    @GET("https://run.mocky.io/v3/e8868481-743f-4eb2-a0d7-2bc4012275c8")
    suspend fun getBooking(): BookingModel
}