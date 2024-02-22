package com.apps10x.weatherx.helpers

import com.apps10x.weatherx.data.Temperature
import com.apps10x.weatherx.data.WeatherForecastResponse
import com.apps10x.weatherx.data.WeatherResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

const val FORECASTS_LIST_SIZE = 40

fun createMockWeatherResponse(city: String, i: Int): WeatherResponse {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return WeatherResponse(
        city,
        Temperature(Random.nextDouble(296.0, 313.0)),
        formatter.format(LocalDate.now().plusDays((i / 8).toLong()).atTime(i % 8, 0))
    )
}

fun createMockForecastData(city: String): WeatherForecastResponse {
    val forecasts = mutableListOf<WeatherResponse>()

    for (i in 0 until FORECASTS_LIST_SIZE) {
        forecasts.add(createMockWeatherResponse(city, i))
    }

    return WeatherForecastResponse(forecasts)
}