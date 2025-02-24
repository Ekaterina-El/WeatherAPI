package ru.elkael.weatherapp.presentations.favorite

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.useCase.GetFavoriteCitiesUseCase
import ru.elkael.weatherapp.domain.useCase.GetWeatherUseCase
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesStore.Intent
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesStore.Label
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesStore.State
import javax.inject.Inject

class FavoriteCitiesStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val getGetWeatherUseCase: GetWeatherUseCase
) {

    fun create(): FavoriteCitiesStore =
        object : FavoriteCitiesStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavoriteStore",
            initialState = State(listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavoriteCitiesLoaded(val cities: List<City>) : Action
    }

    private sealed interface Msg {
        data class FavoriteCitiesLoaded(val cities: List<City>) : Msg
        data class WeatherLoaded(val cityId: Int, val tempC: Int, val conditionUrl: String) : Msg
        data class WeatherLoadingError(val cityId: Int) : Msg
        data class WeatherIsLoading(val cityId: Int) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavoriteCitiesUseCase().collect { cities ->
                    dispatch(Action.FavoriteCitiesLoaded(cities))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickSearch -> publish(Label.ClickSearch)
                Intent.ClickToAddFavorite -> publish(Label.ClickToAddFavorite)
                is Intent.ClickToFavoriteItem -> publish(Label.ClickToFavoriteItem(intent.city))
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavoriteCitiesLoaded -> {
                    val cities = action.cities
                    dispatch(Msg.FavoriteCitiesLoaded(cities = cities))

                    cities.forEach { city ->
                        scope.launch {
                            loadWeatherForCity(cityId = city.id)
                        }
                    }
                }
            }
        }

        private suspend fun loadWeatherForCity(cityId: Int) {
            dispatch(Msg.WeatherIsLoading(cityId))
            try {
                getGetWeatherUseCase(cityId).also {
                    dispatch(
                        Msg.WeatherLoaded(
                            cityId = cityId,
                            tempC = it.tempC,
                            conditionUrl = it.conditionUrl
                        )
                    )
                }
            } catch (e: Exception) {
                dispatch(Msg.WeatherLoadingError(cityId))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.FavoriteCitiesLoaded -> this.copy(cities = msg.cities.map {
                State.CityItem(city = it, weatherState = State.WeatherState.Initial)
            })

            is Msg.WeatherIsLoading -> {
                this.setWeatherStateForCity(
                    cityId = msg.cityId,
                    weatherState = State.WeatherState.Loading
                )
            }

            is Msg.WeatherLoaded -> this.setWeatherStateForCity(
                cityId = msg.cityId,
                weatherState = State.WeatherState.Loaded(
                    tempC = msg.tempC,
                    conditionUrl = msg.conditionUrl,
                )
            )

            is Msg.WeatherLoadingError -> this.setWeatherStateForCity(
                cityId = msg.cityId,
                weatherState = State.WeatherState.Error
            )
        }

        private fun State.setWeatherStateForCity(
            cityId: Int,
            weatherState: State.WeatherState,
        ) = this.copy(cities = this.cities.map {
            if (it.city.id != cityId) return@map it
            it.copy(weatherState = weatherState)
        })
    }
}
