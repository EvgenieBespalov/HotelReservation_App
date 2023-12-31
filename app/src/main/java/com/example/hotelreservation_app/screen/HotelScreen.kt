package com.example.hotelreservation_app.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.domain.entity.HotelEntity
import com.example.hotelreservation_app.R
import com.example.hotelreservation_app.presentation.HotelScreenUiState
import com.example.hotelreservation_app.presentation.HotelScreenViewModel
import com.example.hotelreservation_app.screen.navigation.Routes
import org.koin.androidx.compose.koinViewModel
import java.lang.Math.abs

@Composable
fun HotelScreen(
    navController: NavHostController,
    viewModel: HotelScreenViewModel = koinViewModel(),
){
    val state by viewModel.state.observeAsState(HotelScreenUiState.Initial)

    when(state){
        HotelScreenUiState.Initial    -> viewModel.getHotelData()
        HotelScreenUiState.Loading    -> ScreenLoadind()
        is HotelScreenUiState.Content -> {
            HotelMainInfo(hotelData = (state as HotelScreenUiState.Content).hotelData, navController = navController)
        }
        is HotelScreenUiState.Error   -> ScreenError(errorText = (state as HotelScreenUiState.Error).message.orEmpty())
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun HotelMainInfo(
    hotelData: HotelEntity,
    navController: NavHostController
){
    val pagerState = rememberPagerState(0)
    val pageCount = hotelData.imageUrls.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Column(
            modifier = Modifier
                .padding(start = 21.dp, end = 21.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .height(30.dp)
                    .width(67.dp),
                text = "Отель",
                fontSize = 24.sp,
            )
            Box(
                modifier = Modifier
                    .size(width = 457.dp, height = 300.dp)
                    .padding(top = 0.dp, bottom = 0.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                HorizontalPager(
                    state = pagerState,
                    pageCount = pageCount
                ) { page ->
                    AsyncImage(
                        modifier = Modifier
                            .size(width = 457.dp, height = 300.dp)
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(20.dp)
                            },
                        model = hotelData.imageUrls[pagerState.currentPage],
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

                /*FlowRow(
                    Modifier
                        .padding(10.dp)
                        .size(width = 100.dp, height = 23.dp)
                        .graphicsLayer {
                            clip = true
                            shape = RoundedCornerShape(7.dp)
                        }
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    repeat(pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(10.dp)

                        )
                    }
                }*/
                Row(
                    Modifier
                        .padding(10.dp)
                        .size(width = 70.dp, height = 23.dp)
                        .graphicsLayer {
                            clip = true
                            shape = RoundedCornerShape(7.dp)
                        }
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pageCount) { iteration ->
                        val color = when(abs(iteration - pagerState.currentPage)){
                            0 -> Color(0xFF000000)
                            1 -> Color(0x37000000)
                            2 -> Color(0x2B000000)
                            3 -> Color(0x1A000000)
                            4 -> Color(0xD000000)
                            else -> Color(0x0)
                        }
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(10.dp)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 21.dp, end = 21.dp),
            horizontalAlignment = Alignment.Start
        ){
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 10.dp)
                    .size(width = 175.dp, height = 30.dp)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(7.dp)
                    }
                    .background(color = Color(0x32FFC700)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier
                        .size(19.dp),
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "",
                    tint = Color(0xFFFFA800)
                )
                Text(
                    //modifier = Modifier,
                    color = Color(0xFFFFA800),
                    text = "${hotelData.rating} ${hotelData.ratingName}",
                    fontSize = 19.sp,
                )
            }

            Text(
                modifier = Modifier.padding(top = 0.dp, bottom = 10.dp),
                text = hotelData.name,
                fontSize = 25.sp,
            )

            Text(
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 10.dp)
                    .clickable {  },
                text = hotelData.adress,
                fontSize = 19.sp,
                color = Color(0xFF0D72FF)
            )

            Row(
                modifier = Modifier.padding(top = 0.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = "от ${hotelData.minimalPrice} ₽",
                    fontSize = 30.sp,
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = hotelData.priceForIt,
                    fontSize = 18.sp,
                    color = Color(0xFF828796)
                )
            }
        }

        SpacerBetween()

        val tags = hotelData.peculiarities

        Column(
            modifier = Modifier
                .padding(start = 21.dp, end = 21.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = "Об отеле",
                fontSize = 25.sp,
            )

            FlowRow(
                modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.Start
            ){
                tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp, end = 10.dp)
                            .background(color = Color(0xFFFBFBFC))
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(7.dp)
                            },
                    ){
                        Text(
                            text = tag,
                            fontSize =  18.sp,
                            color = Color(0xFF828796)
                        )
                    }
                }
            }

            Text(
                modifier = Modifier.padding(top = 0.dp, bottom = 20.dp),
                text = hotelData.description,
                fontSize =  18.sp,
            )


            Row(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { }
                    )
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painterResource(id = R.drawable.emoji_happy),
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(35.dp),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(end = 0.dp),
                ){
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = "Удобства",
                        fontSize =  18.sp,
                    )
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = "Самое необходимое",
                        fontSize =  15.sp,
                        color = Color(0xFF828796)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Icon(
                        painterResource(id = R.drawable.chevron_right),
                        modifier = Modifier
                            .size(35.dp),
                        contentDescription = ""
                    )
                }

            }
            Row(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { }
                    )
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painterResource(id = R.drawable.tick_square),
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(35.dp),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(end = 0.dp),
                ){
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = "Что включено",
                        fontSize =  18.sp,
                    )
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = "Самое необходимое",
                        fontSize =  15.sp,
                        color = Color(0xFF828796)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Icon(
                        painterResource(id = R.drawable.chevron_right),
                        modifier = Modifier
                            .size(35.dp),
                        contentDescription = ""
                    )
                }

            }
            Row(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { }
                    )
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painterResource(id = R.drawable.close_square),
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(35.dp),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(end = 0.dp),
                ){
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = "Что не включено",
                        fontSize =  18.sp,
                    )
                    Text(
                        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        text = "Самое необходимое",
                        fontSize =  15.sp,
                        color = Color(0xFF828796)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Icon(
                        painterResource(id = R.drawable.chevron_right),
                        modifier = Modifier
                            .size(35.dp),
                        contentDescription = ""
                    )
                }

            }

        }

        SpacerBetween()

        Button(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(start = 21.dp, top = 10.dp, bottom = 10.dp, end = 21.dp),
            onClick = {
                navController.navigate(Routes.RoomScreenRoute.route + "/${hotelData.name}")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0D72FF),
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp),
        ){
            Text(
                text = "К выбору номера",
                fontSize = 18.sp
            )
        }
    }


}