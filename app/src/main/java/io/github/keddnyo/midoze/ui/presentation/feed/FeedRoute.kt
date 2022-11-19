package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.local.viewmodels.firmware.FirmwareViewModel
import io.github.keddnyo.midoze.remote.requests.download.downloadFirmware
import io.github.keddnyo.midoze.ui.presentation.utils.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FeedRoute(
    viewModel: FirmwareViewModel,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {

    val context = LocalContext.current
    val firmwareList = viewModel.firmwareList.collectAsLazyPagingItems()

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

            if (
                loadState.refresh is LoadState.Loading
                ||
                loadState.append is LoadState.Loading
            ) {
                item {
                    ProgressBar()
                }
            }

            if (
                loadState.refresh is LoadState.Error
                ||
                loadState.append is LoadState.Error
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .padding(32.dp)
                    )
                }
                coroutineScope.launch {
                    val snackBarResult = snackBarHostState.showSnackbar(
                        message = "Error loading feed…",
                        actionLabel = "Retry",
                        duration = SnackbarDuration.Indefinite
                    )
                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                        refresh()
                    }
                }
            }
        }
    }
}