package com.apps10x.weatherx

import androidx.lifecycle.ViewModel
import com.apps10x.weatherx.data.WeatherRepository

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    fun getWeatherToday(city: String) = repository.getWeatherToday(city)

    fun getWeatherForecast(city: String) = repository.getWeatherForecast(city)
}