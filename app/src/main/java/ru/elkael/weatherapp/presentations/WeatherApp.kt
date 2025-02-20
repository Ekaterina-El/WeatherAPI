package ru.elkael.weatherapp.presentations

import android.app.Application
import ru.elkael.weatherapp.di.ApplicationComponent
import ru.elkael.weatherapp.di.DaggerApplicationComponent

class WeatherApp: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}