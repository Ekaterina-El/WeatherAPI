package ru.elkael.weatherapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.elkael.weatherapp.presentations.MainActivity

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        PresentationModule::class
    ]
)
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}