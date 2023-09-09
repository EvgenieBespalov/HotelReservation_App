package com.example.data.api

import com.example.data.model.RoomsModel
import retrofit2.http.GET

interface RoomsApi {
    @GET("/f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    suspend fun getRooms(): RoomsModel
}