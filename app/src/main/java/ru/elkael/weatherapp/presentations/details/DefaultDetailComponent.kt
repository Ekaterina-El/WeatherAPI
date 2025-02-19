package ru.elkael.weatherapp.presentations.details

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

class DefaultDetailComponent @AssistedInject constructor(
    storeFactory: DetailsStoreFactory,
    @Assisted("city") city: City,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
) : DetailComponent,
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(city) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: Flow<DetailsStore.State> get() = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect { label ->
                when (label) {
                    DetailsStore.Label.ClickBack -> onBackClicked
                }
            }
        }
    }

    override fun onClickBack() = store.accept(DetailsStore.Intent.ClickBack)

    override fun onClickIsFavorite() = store.accept(DetailsStore.Intent.ClickFavorite)

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("city") city: City,
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
        ): DefaultDetailComponent
    }
}