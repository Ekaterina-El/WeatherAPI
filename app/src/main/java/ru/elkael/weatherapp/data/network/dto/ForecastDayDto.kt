package ru.elkael.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDayDto(
    @SerializedName("day") val weather: DayWeatherDto,
    @SerializedName("date_epoch") val date: Long,
 )
