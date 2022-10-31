package io.github.keddnyo.midoze.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.local.view_models.MyViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirmwareList(viewModel = viewModel)
        }
    }
}

@Composable
fun FirmwareList(
    viewModel: MyViewModel
) {

    val firmwareList = viewModel.firmwarePager.collectAsLazyPagingItems()

    LazyColumn {
        items(firmwareList) { firmware ->
            firmware?.run {
                ElevatedCard(
                    modifier = Modifier
                        .padding(10.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        firmwareVersion?.let { firmwareVersion ->
                            Text(
                                text = firmwareVersion
                            )
                        }
                        firmwareUrl?.let { firmwareUrl ->
                            Text(
                                text = firmwareUrl
                            )
                        }
                        resourceVersion?.let { resourceVersion ->
                            Text(
                                text = resourceVersion
                            )
                        }
                        resourceUrl?.let { resourceUrl ->
                            Text(
                                text = resourceUrl
                            )
                        }
                    }
                }
            }
        }

        when (firmwareList.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(5.dp)
                        )
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    Text(
                        text = "Error"
                    )
                }
            }
        }
    }
}