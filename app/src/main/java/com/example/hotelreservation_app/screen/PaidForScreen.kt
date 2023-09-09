package com.example.hotelreservation_app.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.hotelreservation_app.R
import com.example.hotelreservation_app.screen.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaidForScreen(navController: NavHostController){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.BookingScreenRoute.route)
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.chevron_left),
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(start = 0.dp),
                        text = "Заказ оплачен",
                        fontSize = 20.sp,
                    )
                }
            )
        },
        content = { padding ->
            PaidForInfo(
                navController = navController,
                padding = padding
            )
        }
    )
}

@Composable
fun PaidForInfo(
    navController: NavHostController,
    padding: PaddingValues
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 21.dp, end = 21.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painterResource(id = R.drawable.hug),
                    modifier = Modifier
                        .padding(top = 0.dp, bottom = 30.dp)
                        .size(90.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp),
                    text = "Ваш заказ принят в работу",
                    fontSize = 25.sp,
                )
                Text(
                    modifier = Modifier,
                    text = "Подтверждение заказа №${(0..10000).random()} может занять некоторое время (от 1 часа до суток). Как только мы получим ответ от туроператора, вам на почту придет уведомление.",
                    fontSize = 19.sp,
                    color = Color(0xFF828796),
                    textAlign = TextAlign.Center
                )
            }

            Button(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                onClick = {
                    navController.navigate(Routes.HotelScreenRoute.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0D72FF),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(15.dp),
            ){
                Text(
                    text = "Супер!",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PaidForScreenPreview(){
    val navController = rememberNavController()
    PaidForScreen(navController)
}