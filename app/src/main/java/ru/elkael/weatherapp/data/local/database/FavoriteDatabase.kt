package ru.elkael.weatherapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.elkael.weatherapp.data.local.dao.FavoriteCitiesDao
import ru.elkael.weatherapp.data.local.model.FavoriteCityDBModel

@Database(
    version = 1,
    entities = [ FavoriteCityDBModel::class ]
)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteCitiesDao(): FavoriteCitiesDao

    companion object {
        private const val DB_NAME = "app_weather_database"
        private val LOCK = Any()

        private lateinit var INSTANCE: FavoriteDatabase

        fun getInstance(context: Context): FavoriteDatabase {
            if (::INSTANCE.isInitialized) return INSTANCE

            synchronized(LOCK) {
                if (::INSTANCE.isInitialized) return INSTANCE

                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = FavoriteDatabase::class.java,
                    name = DB_NAME
                ).build()

                return INSTANCE
            }
        }
    }
}