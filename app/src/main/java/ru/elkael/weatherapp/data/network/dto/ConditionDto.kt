package ru.elkael.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ConditionDto(
    @SerializedName("text") private val text: String,
    @SerializedName("icon") private val iconUrl: String
)