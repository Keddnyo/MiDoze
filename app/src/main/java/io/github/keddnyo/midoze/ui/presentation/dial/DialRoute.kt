package io.github.keddnyo.midoze.ui.presentation.dial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel
import io.github.keddnyo.midoze.ui.presentation.utils.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DialRoute(
    viewModel: WatchfaceViewModel,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {

    val watchfaceList = viewModel.watchfaceList.collectAsLazyPagingItems()
    var localState by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        watchfaceList.run {
            items(this) { watchfaceList ->
                watchfaceList?.let {
                    DialCard(
                        title = watchfaceList.device.deviceName,
                        subtitle = "${watchfaceList.watchfaceList.size} items",
                        preview = watchfaceList.watchfaceList[0].preview
                    ) {

                    }
                }
            }

            @Composable
            fun SnackBar() {
                val message = stringResource(id = R.string.something_went_wrong)
                val retry = stringResource(id = R.string.retry)

                LaunchedEffect(snackBarHostState) {
                    coroutineScope.launch {
                        val snackBarResult = snackBarHostState.showSnackbar(
                            message = message,
                            actionLabel = retry,
                            duration = SnackbarDuration.Indefinite
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            refresh()
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

                    SnackBar()
                }
            }
        }
    }
}