package com.apps10x.weatherx.data

import com.squareup.moshi.Json

data class Temperature(
    @Json(name = "temp")
    val tempKelvin: Double
) {

    companion object {
        private const val KELVIN_TO_CELSIUS = 273.15
    }

    val tempCelsius: Double
        get() = tempKelvin.minus(KELVIN_TO_CELSIUS)
}
