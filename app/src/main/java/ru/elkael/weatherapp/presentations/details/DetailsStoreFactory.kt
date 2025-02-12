package ru.elkael.weatherapp.presentations.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.entities.Forecast
import ru.elkael.weatherapp.domain.useCase.AddToFavoriteUseCase
import ru.elkael.weatherapp.domain.useCase.GetForecastUseCase
import ru.elkael.weatherapp.domain.useCase.ObserveIsFavoriteUseCase
import ru.elkael.weatherapp.domain.useCase.RemoveFromFavoriteUseCase
import ru.elkael.weatherapp.presentations.details.DetailsStore.Intent
import ru.elkael.weatherapp.presentations.details.DetailsStore.Label
import ru.elkael.weatherapp.presentations.details.DetailsStore.State
import ru.elkael.weatherapp.presentations.details.DetailsStore.State.ForecastState

internal class DetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase
) {
    private lateinit var city: City

    fun create(city: City): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
                city = city,
                isFavorite = false,
                forecastState = ForecastState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {
            init {
                // TODO: убедится насколько это правильно
                this@DetailsStoreFactory.city = city
            }
        }

    private sealed interface Action {
        data class IsFavoriteChanged(val value: Boolean) : Action

        data object ForecastLoading: Action
        data object LoadingForecastError: Action
        data class ForecastLoaded(val forecast: Forecast): Action
    }

    private sealed interface Msg {
        data class IsFavoriteChanged(val value: Boolean) : Msg

        data object ForecastLoading: Msg
        data object LoadingForecastError: Msg
        data class ForecastLoaded(val forecast: Forecast): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeIsFavoriteUseCase(cityId = city.id).collect {
                    dispatch(Action.IsFavoriteChanged(it))
                }

                try {
                    dispatch(Action.ForecastLoading)
                    getForecastUseCase(cityId = city.id).also {
                        dispatch(Action.ForecastLoaded(it))
                    }
                } catch (e: Exception) {
                    dispatch(Action.LoadingForecastError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.ClickFavorite -> {
                    scope.launch {
                        if (getState().isFavorite) removeFromFavoriteUseCase(cityId = city.id)
                        else addToFavoriteUseCase(city = city)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.ForecastLoaded -> dispatch(Msg.ForecastLoaded(forecast = action.forecast))
                Action.ForecastLoading -> dispatch(Msg.ForecastLoading)
                Action.LoadingForecastError -> dispatch(Msg.LoadingForecastError)
                is Action.IsFavoriteChanged -> dispatch(Msg.IsFavoriteChanged(action.value))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.IsFavoriteChanged ->  this.copy(isFavorite = msg.value)
            Msg.ForecastLoading -> this.copy(forecastState = ForecastState.Loading)
            Msg.LoadingForecastError -> this.copy(forecastState = ForecastState.LoadingError)
            is Msg.ForecastLoaded -> this.copy(forecastState = ForecastState.Loaded(
                currentWeather = msg.forecast.currentWeather,
                upcomingWeather = msg.forecast.upcoming
            ))
        }
    }
}
