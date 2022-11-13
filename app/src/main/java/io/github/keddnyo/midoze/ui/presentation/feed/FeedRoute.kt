package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.runtime.Composable
import io.github.keddnyo.midoze.local.viewmodels.FirmwareViewModel

@Composable
fun FeedRoute(
    viewModel: FirmwareViewModel
) {
    FeedFirmwareList(
        firmwaresViewModel = viewModel
    )
}