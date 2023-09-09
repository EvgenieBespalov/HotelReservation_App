package com.example.hotelreservation_app.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotelreservation_app.screen.BookingScreen
import com.example.hotelreservation_app.screen.HotelScreen
import com.example.hotelreservation_app.screen.PaidForScreen
import com.example.hotelreservation_app.screen.RoomScreen

@Composable
fun NavHostContainer(
  //  navController: NavHostController,
   // padding: PaddingValues
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HotelScreenRoute.route,
        modifier = Modifier//.padding(paddingValues = padding)
        ,
        builder = {

            composable(Routes.HotelScreenRoute.route) {
                HotelScreen(navController = navController)
            }

            composable(Routes.RoomScreenRoute.route + "/{hotelName}") {
                RoomScreen(navController = navController, hotelName = it.arguments?.getString("hotelName"))
            }

            composable(Routes.PaidForScreenRoute.route) {
                PaidForScreen(navController = navController)
            }

            composable(Routes.BookingScreenRoute.route + "/{hotelName}") {
                BookingScreen(navController = navController, hotelName = it.arguments?.getString("hotelName"))
            }
        }
    )
}