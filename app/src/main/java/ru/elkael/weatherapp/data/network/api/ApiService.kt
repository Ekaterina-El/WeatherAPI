package ru.elkael.weatherapp.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.elkael.weatherapp.data.network.dto.SearchCityDto
import ru.elkael.weatherapp.data.network.dto.WeatherCurrentDto
import ru.elkael.weatherapp.data.network.dto.WeatherForecastDto


interface ApiService {

    @GET("current.json?key=")
    suspend fun loadWeatherCity(
        @Query("q") query: String
    ): WeatherCurrentDto

    @GET("forecast.json?key=")
    suspend fun loadForecastForCity(
        @Query("q") query: String,
        @Query("days") days: Int = 4
    ): WeatherForecastDto

    @GET("search.json?key=")
    suspend fun searchCity(@Query("q") query: String): List<SearchCityDto>
}