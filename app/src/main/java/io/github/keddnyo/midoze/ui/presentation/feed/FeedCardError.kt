package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.runtime.Composable

@Composable
fun FeedCardError(
    message: String?,
    onClick: () -> Unit
) {
    FeedCard(
        title = "Something went wrong…",
        subtitle1 = "Tap on refresh icon to retry",
        summary = message,
        onClick = onClick
    )
}