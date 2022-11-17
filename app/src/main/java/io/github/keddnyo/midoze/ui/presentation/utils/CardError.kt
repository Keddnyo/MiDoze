package io.github.keddnyo.midoze.ui.presentation.utils

import androidx.compose.runtime.Composable
import io.github.keddnyo.midoze.ui.presentation.feed.FeedCard

@Composable
fun FeedCardError(
    message: String?,
    onClick: () -> Unit
) {
    FeedCard(
        title = "Something went wrong…",
        subtitle = "Tap on this card to retry",
        summary = message,
        onClick = onClick
    )
}