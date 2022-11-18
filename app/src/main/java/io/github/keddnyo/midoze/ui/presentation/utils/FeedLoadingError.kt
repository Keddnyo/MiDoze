package io.github.keddnyo.midoze.ui.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.keddnyo.midoze.internal.CardContentOffset

@Composable
fun FeedLoadingError(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(CardContentOffset),
        onClick = onClick
    ) {
        Text(
            text = "Retry"
        )
    }
}