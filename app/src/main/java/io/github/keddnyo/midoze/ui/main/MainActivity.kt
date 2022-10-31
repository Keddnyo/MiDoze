package io.github.keddnyo.midoze.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.R
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

    LazyColumn(
    ) {
        items(firmwareList) { firmware ->
            firmware?.run {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Gray
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.amazfit_bip),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                        ) {
                            Text(
                                text = "Amazfit Bip",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                ),
                            )
                            firmware.firmwareVersion?.let { firmwareVersion ->
                                Text(
                                    text = firmwareVersion
                                )
                            }
                        }
                    }
                    changeLog?.let { changeLog ->
                        Divider(
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                        Text(
                            modifier = Modifier
                                .padding(10.dp),
                            text = changeLog
                        )
                    }
                    buildTime?.let { buildTime ->
                        Divider(
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = buildTime,
                                style = TextStyle(
                                    fontSize = 10.sp
                                ),
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