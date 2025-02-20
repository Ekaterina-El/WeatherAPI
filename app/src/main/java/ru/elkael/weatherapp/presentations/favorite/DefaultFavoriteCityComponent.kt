package ru.elkael.weatherapp.presentations.favorite

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

class DefaultFavoriteCityComponent @AssistedInject constructor(
    storeFactory: FavoriteCitiesStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onSearchClicked") onSearchClicked: () -> Unit,
    @Assisted("onAddFavoriteCityClicked") onAddFavoriteCityClicked: () -> Unit,
    @Assisted("onFavoriteCityClicked") onFavoriteCityClicked: (City) -> Unit
) : FavoriteCitiesComponent,
    ComponentContext by componentContext {
        private val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: Flow<FavoriteCitiesStore.State> get() = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    FavoriteCitiesStore.Label.ClickSearch -> onSearchClicked()
                    FavoriteCitiesStore.Label.ClickToAddFavorite -> onAddFavoriteCityClicked()
                    is FavoriteCitiesStore.Label.ClickToFavoriteItem -> onFavoriteCityClicked(it.city)
                }
            }
        }
    }

    override fun onClickFavoriteCity(city: City) {
        store.accept(FavoriteCitiesStore.Intent.ClickToFavoriteItem(city))
    }

    override fun onClickAddCity() {
        store.accept(FavoriteCitiesStore.Intent.ClickToAddFavorite)
    }

    override fun onClickSearch() {
        store.accept(FavoriteCitiesStore.Intent.ClickSearch)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onSearchClicked") onSearchClicked: () -> Unit,
            @Assisted("onAddFavoriteCityClicked") onAddFavoriteCityClicked: () -> Unit,
            @Assisted("onFavoriteCityClicked") onFavoriteCityClicked: (City) -> Unit
        ): DefaultFavoriteCityComponent
    }
}