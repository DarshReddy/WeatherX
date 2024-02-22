package com.apps10x.weatherx.data

import com.apps10x.weatherx.network.WeatherApiService

class WeatherRepository(private val weatherApiService: WeatherApiService) {

    fun getWeatherToday(city: String) = weatherApiService.getWeatherToday(city = city)
    fun getWeatherForecast(city: String) = weatherApiService.getWeatherForecast(city = city)
}