package ru.elkael.weatherapp.presentations.favorite.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesComponent
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesStore

@Composable
fun FavoriteCityContent(component: FavoriteCitiesComponent) {
    val state by component.state.collectAsState(initial = FavoriteCitiesStore.State(listOf()))

    Scaffold { pv ->
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                horizontal = 10.dp,
                vertical = pv.calculateTopPadding() + 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                SearchCity(onClick = component::onClickSearch)
            }

            itemsIndexed(
                items = state.cities,
                key = { _, item -> item.city.id }
            ) { idx, item ->
                FavoriteCity(
                    idx = idx,
                    item = item,
                    onClick = { component.onClickFavoriteCity(item.city) }
                )
            }

            item {
                AddFavoriteCity(onClick = component::onClickAddCity)
            }
        }
    }
}