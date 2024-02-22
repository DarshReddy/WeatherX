package com.apps10x.weatherx.data

import com.squareup.moshi.Json

data class WeatherForecastResponse(
    @Json(name = "list")
    val list: List<WeatherResponse>
)
