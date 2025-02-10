package ru.elkael.weatherapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteCities")
data class City(
    @PrimaryKey
    val id: Int,

    val name: String,
    val country: String
)