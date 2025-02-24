package ru.elkael.weatherapp.presentations.favorite.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.elkael.weatherapp.presentations.extensions.tempToFormatedString
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesStore
import ru.elkael.weatherapp.presentations.ui.theme.CardGradients


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteCity(idx: Int, item: FavoriteCitiesStore.State.CityItem, onClick: () -> Unit) {
    val gradient = CardGradients.getGradientByIndex(idx)

    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                spotColor = gradient.shadowColor,
                elevation = 16.dp,
                shape = MaterialTheme.shapes.extraLarge
            ),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Box(
            modifier = Modifier
                .background(gradient.primaryColor)
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradient.secondaryColor,
                        center = Offset(
                            x = center.x - size.width / 10,
                            y = center.y + size.width / 2
                        ),
                        radius = size.maxDimension / 2
                    )
                }
                .padding(24.dp)
        ) {
            when (val state = item.weatherState) {
                FavoriteCitiesStore.State.WeatherState.Error -> {}
                FavoriteCitiesStore.State.WeatherState.Initial -> {}
                is FavoriteCitiesStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        model = state.conditionUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .align(Alignment.BottomStart),
                        text = state.tempC.tempToFormatedString(),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 42.sp),
                    )
                }

                FavoriteCitiesStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(56.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleMedium,
                text = item.city.name,
            )
        }
    }
}
