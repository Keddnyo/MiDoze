package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.runtime.Composable

@Composable
fun FeedCardError(
    message: String,
    onClick: () -> Unit
) {
    FeedCard(
        title = "Something went wrong…",
        subtitle1 = message,
        subtitle2 = "Click to retry",
        onClick = onClick
    )
}