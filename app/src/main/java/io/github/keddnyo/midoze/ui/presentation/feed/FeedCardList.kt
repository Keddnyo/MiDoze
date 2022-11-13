package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.local.viewmodels.FirmwareViewModel

@Composable
fun FeedCardList(
    firmwaresViewModel: FirmwareViewModel,
) {

    val firmwareList = firmwaresViewModel.firmwareList.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        firmwareList.run {
            items(this) { firmware ->
                FeedCardFirmware(firmware)
            }

            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        FeedProgressBar()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        FeedProgressBar()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    item {
                        (loadState.refresh as LoadState.Error).error.run {
                            FeedCardError(
                                message = message.toString(),
                                onClick = {
                                    firmwareList.refresh()
                                }
                            )
                        }

                        loadState.refresh
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        (loadState.append as LoadState.Error).error.run {
                            FeedCardError(
                                message = message.toString(),
                                onClick = {
                                    firmwareList.refresh()
                                }
                            )
                        }

                        loadState.append
                    }
                }
            }
        }
    }
}