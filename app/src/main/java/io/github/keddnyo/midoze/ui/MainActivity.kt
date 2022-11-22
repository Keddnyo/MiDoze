package io.github.keddnyo.midoze.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.ui.theme.CardContentOffset
import io.github.keddnyo.midoze.local.ViewModel
import io.github.keddnyo.midoze.remote.requests.downloadFirmware
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiDozeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
                                        wearable.deviceName,
                                        firmwareEntities[0].firmwareVersion,
                                        wearable.devicePreview
                                    ) {
                                        Toast.makeText(context, context.getString(R.string.download), Toast.LENGTH_SHORT)
                                            .show()

                                        firmwareEntities.forEach { firmwareFile ->
                                            downloadFirmware(context, wearable.deviceName, firmwareFile)
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
                                    Box(
                                        Modifier
                                            .padding(CardContentOffset)
                                    ) {
                                        CircularProgressIndicator(
                                            Modifier
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                            }

                            if (
                                loadState.refresh is LoadState.Error
                                ||
                                loadState.append is LoadState.Error
                            ) {
                                item {
                                    FeedCard(
                                        stringResource(R.string.error),
                                        stringResource(R.string.retry)
                                    ) {
                                        refresh()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}