package com.example.hotelreservation_app.screen

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.domain.entity.BookingEntity
import com.example.hotelreservation_app.R
import com.example.hotelreservation_app.presentation.BookingScreenUiState
import com.example.hotelreservation_app.presentation.BookingScreenViewModel
import com.example.hotelreservation_app.presentation.RoomScreenUiState
import com.example.hotelreservation_app.screen.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    navController: NavHostController,
    hotelName: String?,
    viewModel: BookingScreenViewModel = koinViewModel()
){
    val state by viewModel.state.observeAsState(BookingScreenUiState.Initial)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.RoomScreenRoute.route + "/${hotelName}")
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
                        text = "Бронирование",
                        fontSize = 20.sp,
                    )
                }
            )
        },
        content = { padding ->
            when(state){
                BookingScreenUiState.Initial    -> viewModel.getBookingData()
                BookingScreenUiState.Loading    -> ScreenLoadind()
                is BookingScreenUiState.Content -> {
                    BookingInfo(
                        navController = navController,
                        padding = padding,
                        bookingData = (state as BookingScreenUiState.Content).bookingData,
                        hotelName = hotelName
                    )
                }
                is BookingScreenUiState.Error   -> ScreenError(errorText = (state as BookingScreenUiState.Error).message.orEmpty())
            }
        }
    )
}

@Composable
fun BookingInfo(
    navController: NavHostController,
    padding: PaddingValues,
    bookingData: BookingEntity,
    hotelName: String?
){
    var countTourists by remember { mutableStateOf(0) }
    val touristNumber = listOf("Второй", "Третий", "Четвертый", "Пятый", "Шестой", "Седьмой", "Восьмой")

    var nameCorrect by remember { mutableStateOf(false) }
    var lastNameCorrect by remember { mutableStateOf(false) }
    var dateBirthCorrect by remember { mutableStateOf(false) }
    var citizenshipCorrect by remember { mutableStateOf(false) }
    var passportNumberCorrect by remember { mutableStateOf(false) }
    var validityPeriodCorrect by remember { mutableStateOf(false) }

    var userCorrect by remember { mutableStateOf(false) }
    var payCorrect by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ){
        item{
            SpacerBetween()

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
                        text = "${bookingData.horating} ${bookingData.ratingName}",
                        fontSize = 19.sp,
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp),
                    text = bookingData.hotelName,
                    fontSize = 25.sp,
                )

                Text(
                    modifier = Modifier
                        .padding(top = 0.dp, bottom = 10.dp),
                    text = bookingData.hotelAdress,
                    fontSize = 19.sp,
                    color = Color(0xFF0D72FF)
                )
            }

            SpacerBetween()

            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 21.dp),
                horizontalAlignment = Alignment.Start
            ){
                RowWidth(
                    firstText = "Вылет из",
                    secondString = bookingData.departure
                )
                RowWidth(
                    firstText = "Страна, город",
                    secondString = bookingData.arrivalCountry
                )
                RowWidth(
                    firstText = "Даты",
                    secondString = "${bookingData.tourDateStart} – ${bookingData.tourDateStop}"
                )
                RowWidth(
                    firstText = "Кол-во ночей",
                    secondString = "${bookingData.numberOfNights} ночей"
                )
                RowWidth(
                    firstText = "Отель",
                    secondString = bookingData.hotelName
                )
                RowWidth(
                    firstText = "Номер",
                    secondString = bookingData.room
                )
                RowWidth(
                    firstText = "Питание",
                    secondString = bookingData.nutrition
                )
            }

            SpacerBetween()

            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 21.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    text = "Информация о покупателе",
                    fontSize = 25.sp,
                )

                var phoneNumber by remember { mutableStateOf("") }
                var phoneNumberCorrect by remember { mutableStateOf(false) }

                TextField(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 10.dp)
                        .height(65.dp)
                        .fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10)
                            phoneNumber = it
                        if (Patterns.PHONE.matcher(it).matches())
                            phoneNumberCorrect = true
                        else
                            phoneNumberCorrect = false
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Номер телефона",
                            fontSize = 15.sp,
                            color = Color(0xFFA9ABB7)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color(0xFFF6F6F9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 20.sp),
                    visualTransformation = MaskTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                )

                var email by remember { mutableStateOf("") }
                var emailCorrect by remember { mutableStateOf(true) }
                var emailCorrectColor by remember { mutableStateOf(true) }
                TextField(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 10.dp)
                        .height(65.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            emailCorrectColor = emailCorrect
                        },
                    value = email,
                    onValueChange = {
                        if (it.length <= 10)
                            email = it
                        if (Patterns.EMAIL_ADDRESS.matcher(it).matches())
                            emailCorrect = true
                        else
                            emailCorrect = false
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Почта",
                            fontSize = 15.sp,
                            color = Color(0xFFA9ABB7)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = if(emailCorrectColor) Color(0xFFF6F6F9)
                        else Color(0x26EB5757),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Text(
                    modifier = Modifier,
                    text = "Эти данные никому не передаются. После оплаты мы вышли чек на указанный вами номер и почту",
                    fontSize = 15.sp,
                    color = Color(0xFFA9ABB7)
                )
            }

            SpacerBetween()

            var collapsed by remember { mutableStateOf(true) }
            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 21.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                var name by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }
                var dateBirth by remember { mutableStateOf("") }
                var citizenship by remember { mutableStateOf("") }
                var passportNumber by remember { mutableStateOf("") }
                var validityPeriod by remember { mutableStateOf("") }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable {
                            collapsed = !collapsed
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 10.dp)
                            .weight(1f),
                        text = "Первый турист",
                        fontSize = 25.sp,
                    )

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(7.dp)
                            }
                            .size(40.dp)
                            .background(Color(0x1A0D72FF)),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            Icons.Default.run {
                                if (collapsed) KeyboardArrowDown
                                else KeyboardArrowUp
                            },
                            modifier = Modifier
                                .size(30.dp),
                            contentDescription = "",
                            tint = Color(0xFF0D72FF),
                        )
                    }
                }

                if (!collapsed) {
                    TextField(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .height(65.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                userCorrect = nameCorrect && lastNameCorrect && dateBirthCorrect && citizenshipCorrect && passportNumberCorrect && validityPeriodCorrect
                            },
                        value = name,
                        onValueChange = {
                            name = it
                            if (it.length > 0)
                                nameCorrect = true
                            else
                                nameCorrect = false
                        },
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Имя",
                                fontSize = 15.sp,
                                color = Color(0xFFA9ABB7)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = if (nameCorrect || payCorrect) Color(0xFFF6F6F9)
                            else Color(0x26EB5757),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp),
                    )

                    TextField(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .height(65.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                userCorrect = nameCorrect && lastNameCorrect && dateBirthCorrect && citizenshipCorrect && passportNumberCorrect && validityPeriodCorrect
                            },
                        value = lastName,
                        onValueChange = {
                            lastName = it
                            if (it.length > 0)
                                lastNameCorrect = true
                            else
                                lastNameCorrect = false
                        },
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Фамилия",
                                fontSize = 15.sp,
                                color = Color(0xFFA9ABB7)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = if (lastNameCorrect || payCorrect) Color(0xFFF6F6F9)
                            else Color(0x26EB5757),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp)
                    )

                    TextField(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .height(65.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                userCorrect = nameCorrect && lastNameCorrect && dateBirthCorrect && citizenshipCorrect && passportNumberCorrect && validityPeriodCorrect
                            },
                        value = dateBirth,
                        onValueChange = {
                            dateBirth = it
                            if (it.length > 0)
                                dateBirthCorrect = true
                            else
                                dateBirthCorrect = false
                        },
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Дата рождения",
                                fontSize = 15.sp,
                                color = Color(0xFFA9ABB7)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = if (dateBirthCorrect || payCorrect) Color(0xFFF6F6F9)
                            else Color(0x26EB5757),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp)
                    )

                    TextField(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .height(65.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                userCorrect = nameCorrect && lastNameCorrect && dateBirthCorrect && citizenshipCorrect && passportNumberCorrect && validityPeriodCorrect
                            },
                        value = citizenship,
                        onValueChange = {
                            citizenship = it
                            if (it.length > 0)
                                citizenshipCorrect = true
                            else
                                citizenshipCorrect = false
                        },
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Гражданство",
                                fontSize = 15.sp,
                                color = Color(0xFFA9ABB7)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = if (citizenshipCorrect || payCorrect) Color(0xFFF6F6F9)
                            else Color(0x26EB5757),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp)
                    )

                    TextField(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .height(65.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                userCorrect = nameCorrect && lastNameCorrect && dateBirthCorrect && citizenshipCorrect && passportNumberCorrect && validityPeriodCorrect
                            },
                        value = passportNumber,
                        onValueChange = {
                            passportNumber = it
                            if (it.length > 0)
                                passportNumberCorrect = true
                            else
                                passportNumberCorrect = false
                        },
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Номер загранпаспорта",
                                fontSize = 15.sp,
                                color = Color(0xFFA9ABB7)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = if (passportNumberCorrect || payCorrect) Color(0xFFF6F6F9)
                            else Color(0x26EB5757),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp)
                    )

                    TextField(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                            .height(65.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                userCorrect = nameCorrect && lastNameCorrect && dateBirthCorrect && citizenshipCorrect && passportNumberCorrect && validityPeriodCorrect
                            },
                        value = validityPeriod,
                        onValueChange = {
                            validityPeriod = it
                            if (it.length > 0)
                                validityPeriodCorrect = true
                            else
                                validityPeriodCorrect = false
                        },
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Срок действия загранпаспорта",
                                fontSize = 15.sp,
                                color = Color(0xFFA9ABB7)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = if (validityPeriodCorrect || payCorrect) Color(0xFFF6F6F9)
                            else Color(0x26EB5757),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp)
                    )
                }
            }
        }

        items(countTourists){
            SpacerBetween()
            OtherTouristData(touristNumber[it])
        }

        item{
            SpacerBetween()

            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 21.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.Start
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 10.dp)
                            .weight(1f),
                        text = "Добавить туриста",
                        fontSize = 25.sp,
                    )

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(7.dp)
                            }
                            .size(40.dp)
                            .background(Color(0xFF0D72FF)),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            painterResource(id = R.drawable.plus),
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (countTourists < 8)
                                        countTourists++
                                },
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }

            SpacerBetween()

            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 21.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.Start
            ){
                RowWeight(firstText = "Тур", secondString = bookingData.tourPrice, colorSecond = Color.Black)
                RowWeight(firstText = "Топливный сбор", secondString = bookingData.fuelCharge, colorSecond = Color.Black)
                RowWeight(firstText = "Сервисный сбор", secondString = bookingData.serviceCharge, colorSecond = Color.Black)
                RowWeight(
                    firstText = "К оплате",
                    secondString = (bookingData.tourPrice.toInt() + bookingData.fuelCharge.toInt() + bookingData.serviceCharge.toInt()).toString(),
                    colorSecond = Color(0xFF0D72FF)
                )
            }

            SpacerBetween()

            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 21.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.Start
            ){
                Button(
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 10.dp),
                    onClick = {
                        if (userCorrect)
                            navController.navigate(Routes.PaidForScreenRoute.route + "/${hotelName}")
                        else
                            payCorrect = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(payCorrect || userCorrect) Color(0xFF0D72FF)
                        else Color(0x26EB5757),
                        contentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(15.dp),
                ){
                    Text(
                        text = "Оплатить ${(bookingData.tourPrice.toInt() + bookingData.fuelCharge.toInt() + bookingData.serviceCharge.toInt()).toString()} ₽",
                        fontSize = 18.sp
                    )
                }
            }
        }

    }
}

@Composable
fun OtherTouristData(touristNumber: String){
    var collapsed by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .padding(start = 21.dp, end = 21.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        var name by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var dateBirth by remember { mutableStateOf("") }
        var citizenship by remember { mutableStateOf("") }
        var passportNumber by remember { mutableStateOf("") }
        var validityPeriod by remember { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    collapsed = !collapsed
                }
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 10.dp)
                    .weight(1f),
                text = "${touristNumber} турист",
                fontSize = 25.sp,
            )

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(7.dp)
                    }
                    .size(40.dp)
                    .background(Color(0x1A0D72FF)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Default.run {
                        if (collapsed) KeyboardArrowDown
                        else KeyboardArrowUp
                    },
                    modifier = Modifier
                        .size(30.dp),
                    contentDescription = "",
                    tint = Color(0xFF0D72FF),
                )
            }
        }

        if (!collapsed) {
            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .height(65.dp)
                    .fillMaxWidth(),
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Имя",
                        fontSize = 15.sp,
                        color = Color(0xFFA9ABB7)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFF6F6F9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 20.sp),
            )

            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .height(65.dp)
                    .fillMaxWidth(),
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Фамилия",
                        fontSize = 15.sp,
                        color = Color(0xFFA9ABB7)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFF6F6F9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 20.sp)
            )

            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .height(65.dp)
                    .fillMaxWidth(),
                value = dateBirth,
                onValueChange = {
                    dateBirth = it
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Дата рождения",
                        fontSize = 15.sp,
                        color = Color(0xFFA9ABB7)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFF6F6F9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 20.sp)
            )

            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .height(65.dp)
                    .fillMaxWidth(),
                value = citizenship,
                onValueChange = {
                    citizenship = it
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Гражданство",
                        fontSize = 15.sp,
                        color = Color(0xFFA9ABB7)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFF6F6F9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 20.sp)
            )

            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .height(65.dp)
                    .fillMaxWidth(),
                value = passportNumber,
                onValueChange = {
                    passportNumber = it
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Номер загранпаспорта",
                        fontSize = 15.sp,
                        color = Color(0xFFA9ABB7)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFF6F6F9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 20.sp)
            )

            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .height(65.dp)
                    .fillMaxWidth(),
                value = validityPeriod,
                onValueChange = {
                    validityPeriod = it
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Срок действия загранпаспорта",
                        fontSize = 15.sp,
                        color = Color(0xFFA9ABB7)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFF6F6F9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 20.sp)
            )
        }
    }
}


@Composable
fun RowWidth(firstText: String, secondString: String){
    Row(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
    ){
        Text(
            modifier = Modifier.width(150.dp),
            text = firstText,
            fontSize = 17.sp,
            color = Color(0xFF828796)
        )
        Text(
            modifier = Modifier,
            text = secondString,
            fontSize = 17.sp
        )
    }
}

@Composable
fun RowWeight(firstText: String, secondString: String, colorSecond: Color){
    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
    ){
        Text(
            modifier = Modifier.weight(1f),
            text = firstText,
            fontSize = 19.sp,
            color = Color(0xFF828796)
        )
        Text(
            modifier = Modifier,
            text = "${secondString} ₽",
            fontSize = 19.sp,
            color = colorSecond
        )
    }
}

class MaskTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }
}

fun maskFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text
    var out = ""
    for (i in trimmed.indices) {
        when(i){
            0 -> out += "+7 (" + trimmed[i]
            2 -> out += trimmed[i] + ") "
            5 -> out += trimmed[i] + "-"
            7 -> out += trimmed[i] + "-"
            else -> out += trimmed[i]
        }
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                in (1..2) -> offset + 4
                in (3..5) -> offset + 6
                in (6..7) -> offset + 7
                in (8..10) -> offset + 8
                else -> offset
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                in (1..6) -> offset - 4
                in (7..11) -> offset - 6
                in (12..14) -> offset - 7
                in (15..18) -> offset - 8
                else -> offset
            }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}