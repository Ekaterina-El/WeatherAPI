package ru.elkael.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("last_updated_epoch") val lastUpdate: Long,
    @SerializedName("temp_c") val tempC: Float,
    @SerializedName("condition") val condition: ConditionDto,
)
