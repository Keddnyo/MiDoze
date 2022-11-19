package io.github.keddnyo.midoze.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.keddnyo.midoze.local.viewmodels.firmware.FirmwareViewModel
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel
import io.github.keddnyo.midoze.ui.presentation.dial.DialRoute
import io.github.keddnyo.midoze.ui.presentation.feed.FeedRoute
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavigationGraph(
    navController: NavHostController,
    feedViewModel: FirmwareViewModel,
    dialViewModel: WatchfaceViewModel,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Apps.route
    ) {
        composable(NavigationItem.Apps.route) {
            Text(
                text = stringResource(
                    id = NavigationItem.Apps.title
                )
            )
        }
        composable(NavigationItem.Feed.route) {
            FeedRoute(
                viewModel = feedViewModel,
                snackBarHostState = snackBarHostState,
                coroutineScope = coroutineScope
            )
        }
        composable(NavigationItem.Dials.route) {
            DialRoute(
                viewModel = dialViewModel,
                snackBarHostState = snackBarHostState,
                coroutineScope = coroutineScope
            )
        }
    }
}