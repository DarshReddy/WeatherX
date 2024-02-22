package com.apps10x.weatherx.data

import com.squareup.moshi.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class WeatherResponse(
    @Json(name = "name")
    val city: String?,
    @Json(name = "main")
    val temperatureData: Temperature,
    @Json(name = "dt_txt")
    val dateTimeText: String?
) {

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }

    @Transient
    private val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)

    @Transient
    val date = dateTimeText?.let { LocalDate.parse(it, dateFormatter) }
}
