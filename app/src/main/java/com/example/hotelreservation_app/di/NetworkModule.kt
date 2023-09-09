package com.example.hotelreservation_app.di

import android.service.controls.ControlsProviderService
import android.util.Log
import com.example.data.api.BookingApi
import com.example.data.api.HotelApi
import com.example.data.api.RoomsApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://run.mocky.io/v3/"
private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L
private const val READ_TIMEOUT = 10L

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient().newBuilder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .build()

private fun provideGson(): Gson =
    GsonBuilder()
        .create()


private fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

private fun provideHotelApi(retrofit: Retrofit): HotelApi =
    retrofit.create()

private fun provideRoomsApi(retrofit: Retrofit): RoomsApi =
    retrofit.create()

private fun provideBookingApi(retrofit: Retrofit): BookingApi =
    retrofit.create()

fun provideNetworkModule(): Module =
    module {
        single { provideOkHttpClient() }
        single { provideGson() }
        single { provideRetrofit(okHttpClient = get(), gson = get()) }
        single { provideHotelApi(retrofit = get()) }
        single { provideRoomsApi(retrofit = get()) }
        single { provideBookingApi(retrofit = get()) }
    }