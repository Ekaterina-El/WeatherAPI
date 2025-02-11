package ru.elkael.weatherapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.elkael.weatherapp.data.local.database.FavoriteDatabase
import ru.elkael.weatherapp.data.network.api.ApiFactory
import ru.elkael.weatherapp.data.repository.FavoriteRepositoryImpl
import ru.elkael.weatherapp.data.repository.SearchRepositoryImpl
import ru.elkael.weatherapp.data.repository.WeatherRepositoryImpl
import ru.elkael.weatherapp.domain.repository.FavoriteRepository
import ru.elkael.weatherapp.domain.repository.SearchRepository
import ru.elkael.weatherapp.domain.repository.WeatherRepository

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideFavoriteDatabase(context: Context) = FavoriteDatabase.getInstance(context)

        @[ApplicationScope Provides]
        fun provideFavoriteCitiesDao(db: FavoriteDatabase) = db.favoriteCitiesDao()

        @[ApplicationScope Provides]
        fun provideApiService() = ApiFactory.apiService
    }
}