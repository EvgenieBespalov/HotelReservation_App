package com.example.hotelreservation_app.screen.navigation

sealed class Routes(val route: String) {
    object HotelScreenRoute : Routes("HotelScreenRoute")
    object RoomScreenRoute : Routes("RoomScreenRoute")
    object PaidForScreenRoute : Routes("PaidForScreenRoute")
    object BookingScreenRoute : Routes("BookingScreenRoute")
}