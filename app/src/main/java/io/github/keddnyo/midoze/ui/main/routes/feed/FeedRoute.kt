package io.github.keddnyo.midoze.ui.main.routes.feed

import androidx.compose.runtime.Composable
import io.github.keddnyo.midoze.local.viewmodels.FirmwareViewModel

@Composable
fun FeedRoute(
    viewModel: FirmwareViewModel
) {
    FeedCardList(
        firmwaresViewModel = viewModel
    )
}