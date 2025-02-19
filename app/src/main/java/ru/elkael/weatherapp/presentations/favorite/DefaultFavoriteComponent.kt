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

class DefaultFavoriteComponent @AssistedInject constructor(
    storeFactory: FavoriteStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onSearchClicked") onSearchClicked: () -> Unit,
    @Assisted("onAddFavoriteCityClicked") onAddFavoriteCityClicked: () -> Unit,
    @Assisted("onFavoriteCityClicked") onFavoriteCityClicked: (City) -> Unit
) : FavoriteComponent,
    ComponentContext by componentContext {
        private val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: Flow<FavoriteStore.State> get() = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    FavoriteStore.Label.ClickSearch -> onSearchClicked()
                    FavoriteStore.Label.ClickToAddFavorite -> onAddFavoriteCityClicked()
                    is FavoriteStore.Label.ClickToFavoriteItem -> onFavoriteCityClicked(it.city)
                }
            }
        }
    }

    override fun onClickFavoriteCity(city: City) {
        store.accept(FavoriteStore.Intent.ClickToFavoriteItem(city))
    }

    override fun onClickAddCity() {
        store.accept(FavoriteStore.Intent.ClickToAddFavorite)
    }

    override fun onClickSearch() {
        store.accept(FavoriteStore.Intent.ClickSearch)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onSearchClicked") onSearchClicked: () -> Unit,
            @Assisted("onAddFavoriteCityClicked") onAddFavoriteCityClicked: () -> Unit,
            @Assisted("onFavoriteCityClicked") onFavoriteCityClicked: (City) -> Unit
        ): DefaultFavoriteComponent
    }
}