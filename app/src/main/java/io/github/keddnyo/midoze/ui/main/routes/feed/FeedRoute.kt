package io.github.keddnyo.midoze.ui.main.routes.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.keddnyo.midoze.local.viewmodels.FirmwareViewModel

@Composable
fun FeedRoute(
    padding: PaddingValues,
    viewModel: FirmwareViewModel
) {
    Box(
        modifier = Modifier
            .padding(padding),
    ) {
        FirmwareList(
            firmwaresViewModel = viewModel
        )
    }
}