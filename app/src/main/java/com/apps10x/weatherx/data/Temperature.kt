package com.apps10x.weatherx.data

import com.squareup.moshi.Json

data class Temperature(
    @Json(name = "temp")
    val tempKelvin: Double?
) {
    val tempCelsius: Int?
        get() = (tempKelvin?.minus(273.15))?.toInt()
}
