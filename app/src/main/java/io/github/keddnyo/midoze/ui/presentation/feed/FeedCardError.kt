package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.runtime.Composable

@Composable
fun FeedCardError(
    onClick: () -> Unit
) {
    FeedCard(
        title = "Something went wrong…",
        subtitle2 = "Top on \"Refresh\" icon to retry",
        onClick = onClick
    )
}