package io.github.keddnyo.midoze.ui.presentation.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.keddnyo.midoze.internal.CardContentOffset

@Composable
fun FeedLoadingError(
    message: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(CardContentOffset),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message.toString()
        )
        Button(
            modifier = Modifier
                .padding(CardContentOffset),
            onClick = onClick
        ) {
            Text(
                text = "Try again"
            )
        }
    }
}