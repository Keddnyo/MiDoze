package io.github.keddnyo.midoze.ui.presentation.dial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel
import io.github.keddnyo.midoze.ui.presentation.ProgressBar
import io.github.keddnyo.midoze.ui.presentation.FeedCardError

@Composable
fun DialRoute(
    viewModel: WatchfaceViewModel
) {

    val watchfaceList = viewModel.watchfaceList.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        watchfaceList.run {
            items(this) { watchfaceList ->
                DialCard(
                    preview = watchfaceList!!.preview,
                    title = watchfaceList.device.deviceName,
                    summary = "${watchfaceList.watchfaceList.size} items"
                ) {

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
                            FeedCardError(
                                message = message,
                                onClick = {
                                    watchfaceList.refresh()
                                }
                            )
                        }
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        (loadState.append as LoadState.Error).error.run {
                            FeedCardError(
                                message = message,
                                onClick = {
                                    watchfaceList.refresh()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}