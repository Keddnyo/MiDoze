package io.github.keddnyo.midoze.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import io.github.keddnyo.midoze.local.view_models.FirmwareViewModel
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme

class MainActivity : ComponentActivity() {

    private val firmwaresViewModel by viewModels<FirmwareViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiDozeTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "MiDoze"
                                )
                            }
                        )
                    },
                    content = { padding ->
                        Box(
                            modifier = Modifier
                                .padding(padding)
                        ) {
                            FirmwareList(firmwaresViewModel = firmwaresViewModel)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FirmwareList(
    firmwaresViewModel: FirmwareViewModel,
) {

    val firmwareList = firmwaresViewModel.firmwareList.collectAsLazyPagingItems()

    LazyColumn {
        items(firmwareList) { firmware ->
            firmware?.run {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.Gray
                            ),
                            shape = RoundedCornerShape(10.dp),
                        ),
                    elevation = CardDefaults.outlinedCardElevation(5.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_zepp_life),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(50.dp),
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = Color.Gray
                                    ),
                                    shape = RoundedCornerShape(50.dp),
                                ),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                        ) {
                            Text(
                                text = "Zepp Life",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                            buildTime?.let {
                                Text(
                                    text = "13.09.2022 16:48",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                    ),
                                )
                            }
                        }
                    }
                    Image(
                        painter = painterResource(R.drawable.amazfit_bip),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(196.dp)
                            .background(
                                color = Color.White,
                            )
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Color.Gray
                                ),
                            )
                            .padding(15.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                    firmwareVersion?.let { firmwareVersion ->
                        changeLog?.let { changeLog ->
                            val changeLogText = StringBuilder()
                                .append("Unknown device")
                                .append(System.getProperty("line.separator"))
                                .append("Firmware version: $firmwareVersion")
                                .append(System.getProperty("line.separator"))
                                .append(changeLog)
                                .toString()

                            Text(
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                                text = changeLogText,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                ),
                            )
                        }
                    }
                    Divider(
                        thickness = 0.5.dp,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        device.run {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterVertically),
                                text = "$deviceSource, $productionSource",
                                style = TextStyle(
                                    fontSize = 8.sp,
                                ),
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(10.dp),
                            onClick = { /*TODO*/ },
                        ) {
                            Text(
                                text = "Download"
                            )
                        }
                    }
                }
            }
        }

        firmwareList.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error"
                        )
                    }
                }
            }
        }
    }
}