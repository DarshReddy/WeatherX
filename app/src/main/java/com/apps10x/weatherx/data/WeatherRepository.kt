package com.apps10x.weatherx.data

import com.apps10x.weatherx.network.WeatherApiService
import com.apps10x.weatherx.utils.doSafeApiRequest
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApiService: WeatherApiService) {

    suspend fun getWeatherToday(city: String) =
        doSafeApiRequest<WeatherResponse> { weatherApiService.getWeatherToday(city = city) }

    suspend fun getWeatherForecast(city: String) =
        doSafeApiRequest<WeatherForecastResponse> { weatherApiService.getWeatherForecast(city = city) }

}
