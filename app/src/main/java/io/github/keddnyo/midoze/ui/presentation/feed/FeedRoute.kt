package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.local.viewmodels.firmware.FirmwareViewModel
import io.github.keddnyo.midoze.remote.requests.download.downloadFirmware
import io.github.keddnyo.midoze.ui.presentation.utils.FeedCard
import io.github.keddnyo.midoze.ui.presentation.utils.FeedLoadingError
import io.github.keddnyo.midoze.ui.presentation.utils.ProgressBar

@Composable
fun FeedRoute(
    viewModel: FirmwareViewModel
) {

    val firmwareList = viewModel.firmwareList.collectAsLazyPagingItems()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        firmwareList.run {
            items(this) { firmware ->
                firmware?.run {
                    FeedCard(
                        title = wearable.deviceName,
                        subtitle = firmwareEntities[0].firmwareVersion,
                        icon = wearable.devicePreview
                    ) {
                        firmwareEntities.forEach { firmwareFile ->
                            downloadFirmware(
                                context = context,
                                deviceName = wearable.deviceName,
                                firmwareEntity = firmwareFile
                            )
                        }
                    }
                }
            }

            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        ProgressBar()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        ProgressBar()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    item {
                        (loadState.refresh as LoadState.Error).error.run {
                            FeedLoadingError {
                                firmwareList.refresh()
                            }
                        }
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        (loadState.append as LoadState.Error).error.run {
                            FeedLoadingError {
                                firmwareList.refresh()
                            }
                        }
                    }
                }
            }
        }
    }
}