package com.apps10x.weatherx.data

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "name")
    val city: String?,
    @Json(name = "main")
    val main: Temperature,
    @Json(name = "dt")
    val timeStamp: Long,
    @Json(name = "dt_txt")
    val dateText: String?
)
