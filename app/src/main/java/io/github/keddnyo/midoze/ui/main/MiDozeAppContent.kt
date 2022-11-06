package io.github.keddnyo.midoze.ui.main

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.view_models.FirmwareViewModel
import io.github.keddnyo.midoze.ui.main.routes.feed.FeedRoute
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiDozeAppContent(
    viewModel: FirmwareViewModel
) {
    MiDozeTheme {
        Scaffold(
            topBar = {
                TopAppBar()
            },
            content = { padding ->
                FeedRoute(
                    padding = padding,
                    viewModel = viewModel,
                )
            }
        )
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