package com.apps10x.weatherx.network

import com.apps10x.weatherx.data.WeatherForecastResponse
import com.apps10x.weatherx.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/weather")
    fun getWeatherToday(
        @Query("APPID") apiKey: String = BuildConfig.appId,
        @Query("q") city: String,
    ): Response<WeatherResponse>

    @GET("/forecast")
    fun getWeatherForecast(
        @Query("APPID") apiKey: String = BuildConfig.appId,
        @Query("q") city: String,
    ): Response<WeatherForecastResponse>
}