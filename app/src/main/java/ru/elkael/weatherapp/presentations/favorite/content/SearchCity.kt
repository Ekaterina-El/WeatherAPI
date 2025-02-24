package ru.elkael.weatherapp.presentations.favorite.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.elkael.weatherapp.R
import ru.elkael.weatherapp.presentations.ui.theme.CardGradients

@Composable
fun SearchCity(onClick: () -> Unit) {
    val gradient = CardGradients.searchGradient

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.search_city_placeholder),
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}
