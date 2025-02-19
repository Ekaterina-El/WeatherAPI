package ru.elkael.weatherapp.presentations.search

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.useCase.SearchCityUseCase
import ru.elkael.weatherapp.presentations.search.SearchStore.Intent
import ru.elkael.weatherapp.presentations.search.SearchStore.Label
import ru.elkael.weatherapp.presentations.search.SearchStore.State
import javax.inject.Inject

internal class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchCityUseCase: SearchCityUseCase
) {

    private var searchCityJob: Job? = null

    fun create(openReason: OpenReason): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = State(
                searchText = "",
                state = State.Companion.SearchState.Initialized,
                reason = openReason
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data object LoadingCities: Msg
        data object LoadingCitiesError: Msg
        data class CitiesLoaded(val cities: List<City>): Msg
        data class ChangeSearchText(val value: String): Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {}
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> publish(Label.GoBack)

                is Intent.ClickOnCity -> when (getState().reason) {
                    OpenReason.SHOW_CITY_WEATHER -> publish(Label.ShowCityWeather)
                    OpenReason.ADD_FAVORITE -> publish(Label.AddFavorite)
                }

                is Intent.OnChangeSearchText -> dispatch(Msg.ChangeSearchText(intent.value))
                Intent.ClickSearch -> {
                    searchCityJob?.cancel()
                    searchCityJob = scope.launch {
                        dispatch(Msg.LoadingCities)
                        try {
                            val cities = searchCityUseCase(getState().searchText)
                            dispatch(Msg.CitiesLoaded(cities))
                        } catch (e: Exception) {
                            dispatch(Msg.LoadingCitiesError)
                        }
                    }
                }

            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.CitiesLoaded -> {
                if (msg.cities.isEmpty()) this.copy(state = State.Companion.SearchState.EmptyResult)
                else this.copy(state = State.Companion.SearchState.SuccessLoaded(msg.cities))
            }

            Msg.LoadingCities -> this.copy(state = State.Companion.SearchState.Loading)
            Msg.LoadingCitiesError -> this.copy(state = State.Companion.SearchState.LoadingError)
            is Msg.ChangeSearchText -> this.copy(searchText = msg.value)
        }
    }
}
