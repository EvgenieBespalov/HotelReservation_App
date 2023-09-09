package com.example.hotelreservation_app.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
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
import com.example.domain.entity.RoomEnity
import com.example.hotelreservation_app.R
import com.example.hotelreservation_app.presentation.HotelScreenUiState
import com.example.hotelreservation_app.presentation.RoomScreenUiState
import com.example.hotelreservation_app.presentation.RoomScreenViewModel
import com.example.hotelreservation_app.screen.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomScreen(
    navController: NavHostController,
    hotelName: String?,
    viewModel: RoomScreenViewModel = koinViewModel()
){
    val state by viewModel.state.observeAsState(RoomScreenUiState.Initial)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.HotelScreenRoute.route)
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.chevron_left),
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = hotelName ?: "Отель",
                            fontSize = 20.sp,
                        )
                    }
                }
            )
        },
        content = { padding ->
            when(state){
                RoomScreenUiState.Initial    -> viewModel.getRoomsData()
                RoomScreenUiState.Loading    -> ScreenLoadind()
                is RoomScreenUiState.Content -> {
                    RoomList(
                        roomsData = (state as RoomScreenUiState.Content).roomsData,
                        navController = navController,
                        padding = padding,
                        hotelName = hotelName
                    )
                }
                is RoomScreenUiState.Error   -> ScreenError(errorText = (state as RoomScreenUiState.Error).message.orEmpty())
            }
        }
    )
}

@Composable
fun RoomList(
    roomsData: List<RoomEnity>,
    navController: NavHostController,
    padding: PaddingValues,
    hotelName: String?
){
    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .background(Color(0xFFF6F6F9)),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        item { Spacer(modifier = Modifier
            .height(0.dp)
            .fillMaxWidth()) }
        items(roomsData.size){
            RoomInfo(
                navController = navController,
                roomData = roomsData[it],
                hotelName = hotelName
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun RoomInfo(
    navController: NavHostController,
    roomData: RoomEnity,
    hotelName: String?
){
    val pagerState = rememberPagerState(0)
    val pageCount = roomData.imageUrls.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Column(
            modifier = Modifier
                .padding(start = 21.dp, top = 21.dp, end = 21.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
                        model = roomData.imageUrls[pagerState.currentPage],
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

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
                        val color = when(Math.abs(iteration - pagerState.currentPage)){
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
            Text(
                modifier = Modifier.padding(top = 0.dp, bottom = 10.dp),
                text = roomData.name,
                fontSize = 25.sp,
            )

            val tags = roomData.peculiarities
            FlowRow(
                modifier = Modifier.padding(start = 10.dp, top = 0.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.Start
            ){
                tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp, end = 10.dp)
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(7.dp)
                            }
                            .background(color = Color(0xFFFBFBFC)),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = tag,
                            fontSize =  18.sp,
                            color = Color(0xFF828796)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 0.dp, bottom = 10.dp)
                    .background(Color(0x1A0D72FF)),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {  },
                    text = "Подробнее о номере",
                    fontSize = 19.sp,
                    color = Color(0xFF0D72FF)
                )
                Icon(
                    painterResource(id = R.drawable.chevron_right),
                    modifier = Modifier
                        .size(35.dp),
                    contentDescription = "chevron_right",
                    tint = Color(0xFF0D72FF)
                )
            }

            Row(
                modifier = Modifier.padding(top = 0.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = "${roomData.price} ₽",
                    fontSize = 30.sp,
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = roomData.pricePer,
                    fontSize = 18.sp,
                    color = Color(0xFF828796)
                )
            }
            Button(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 10.dp),
                onClick = {
                    navController.navigate(Routes.BookingScreenRoute.route + "/${hotelName}")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0D72FF),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(15.dp),
            ){
                Text(
                    text = "Выбрать номер",
                    fontSize = 18.sp
                )
            }
        }
    }
}
