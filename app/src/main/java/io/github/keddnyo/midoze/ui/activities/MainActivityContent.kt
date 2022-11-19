package io.github.keddnyo.midoze.ui.activities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.viewmodels.firmware.FirmwareViewModel
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel
import io.github.keddnyo.midoze.ui.presentation.dial.DialRoute
import io.github.keddnyo.midoze.ui.presentation.feed.FeedRoute
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiDozeAppContent(
    feedViewModel: FirmwareViewModel,
    dialViewModel: WatchfaceViewModel
) {
    MiDozeTheme {
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopAppBar()
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding),
            ) {
                FeedRoute(
                    viewModel = feedViewModel,
                    snackBarHost = snackBarHostState,
                    coroutineScope = scope
                )
//                DialRoute(
//                    viewModel = dialViewModel,
//                    snackBarHost = snackBarHostState,
//                    coroutineScope = scope
//                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(
                    id = R.string.app_name
                )
            )
        }
    )
}