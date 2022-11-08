package io.github.keddnyo.midoze.ui.main.routes.feed

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
        firmwareList.apply {
            items(this) { firmware ->
                FeedCard(firmware)
            }

            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        FeedLoadingBar()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        FeedLoadingBar()
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        FeedLoadingErrorMessage()
                    }
                }
            }
        }
    }
}