package ru.elkael.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.elkael.weatherapp.data.local.model.City

@Dao
interface FavoriteCitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(city: City)

    @Delete
    suspend fun removeFavoriteCity(cityId: Int)

    @Query("SELECT * FROM favoriteCities")
    fun getFavoriteCities(): Flow<List<City>>

    @Query("SELECT EXISTS (SELECT * FROM favoriteCities WHERE id=:cityId LIMIT 1)")
    fun observeIsFavourite(cityId: Int): Flow<Boolean>
}