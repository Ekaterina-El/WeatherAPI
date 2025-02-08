package ru.elkael.weatherapp.domain.entities

import java.util.Calendar

data class Weather(
    val tempC: Int,
    val conditionText: String,
    val conditionUrl: String,
    val date: Calendar
)
