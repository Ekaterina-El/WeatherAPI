package ru.elkael.weatherapp.presentations.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.presentations.extensions.componentScope

class DefaultSearchComponent @AssistedInject constructor(
    storeFactory: SearchStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("openReason") openReason: OpenReason,
    @Assisted("onBackClicked") onBackClicked: () -> Unit,
    @Assisted("onAddedCityToFavorite") onAddedCityToFavorite: () -> Unit,
    @Assisted("onShowCityWeather") onShowCityWeather: (City) -> Unit
) : SearchComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore { storeFactory.create(openReason = openReason) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: Flow<SearchStore.State> get() = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect { label ->
                when (label) {
                    SearchStore.Label.CityAddedToFavorites -> onAddedCityToFavorite()
                    SearchStore.Label.GoBack -> onBackClicked()
                    is SearchStore.Label.ShowCityWeather -> onShowCityWeather(label.city)
                }
            }
        }
    }

    override fun onClickSearch() = store.accept(SearchStore.Intent.ClickSearch)
    override fun onClickBack() = store.accept(SearchStore.Intent.ClickBack)
    override fun onSelectCity(city: City) = store.accept(SearchStore.Intent.ClickOnCity(city))
    override fun onChangeSearchValue(value: String) = store.accept(SearchStore.Intent.OnChangeSearchText(value))

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("openReason") openReason: OpenReason,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onAddedCityToFavorite") onAddedCityToFavorite: () -> Unit,
            @Assisted("onShowCityWeather") onShowCityWeather: (City) -> Unit
        ): DefaultSearchComponent
    }
}