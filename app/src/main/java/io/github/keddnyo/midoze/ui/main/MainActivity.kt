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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import io.github.keddnyo.midoze.local.database.FirmwareRepository
import io.github.keddnyo.midoze.local.view_models.FirmwaresViewModel
import io.github.keddnyo.midoze.local.view_models.MyViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MyViewModel>()
    private val firmwaresViewModel by viewModels<FirmwaresViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirmwareList(viewModel = viewModel, firmwaresViewModel = firmwaresViewModel)
        }
    }
}

@Composable
fun FirmwareList(
    viewModel: MyViewModel,
    firmwaresViewModel: FirmwaresViewModel
) {

//    val firmwareList = viewModel.firmwarePager.collectAsLazyPagingItems()
    val firmwareList = firmwaresViewModel.firmwares.sortedBy { it.buildTime }.asReversed()

    LazyColumn(
    ) {
        itemsIndexed(firmwareList) { index, firmware ->
            firmware.run {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    elevation = CardDefaults.outlinedCardElevation(5.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.amazfit_bip),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .border(
                                    border = BorderStroke(
                                        0.5.dp,
                                        Color.Gray
                                    ),
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(50.dp),
                                )
                                .padding(5.dp)
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
                                    fontSize = 16.sp,
                                ),
                            )
                            buildTime?.let { buildTime ->
                                Text(
                                    text = buildTime,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                    ),
                                )
                            }
                        }
                    }
                    changeLog?.let { changeLog ->
                        Text(
                            modifier = Modifier
                                .padding(10.dp),
                            text = changeLog
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(top = 10.dp, end = 10.dp, bottom = 10.dp),
                            onClick = { /*TODO*/ },
                        ) {
                            Text(
                                text = "Download"
                            )
                        }
                    }
                }

//                firmwaresViewModel.addFirmware(
//                    FirmwareDataModel(
//                        id = index,
//                        firmwareVersion = firmwareVersion,
//                        firmwareUrl = firmwareUrl,
//                        resourceVersion = resourceVersion,
//                        resourceUrl = resourceUrl,
//                        changeLog = changeLog,
//                        buildTime = buildTime,
//                    )
//                )
            }
        }

//        when (firmwareList.loadState.append) {
//            is LoadState.NotLoading -> Unit
//            LoadState.Loading -> {
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .padding(5.dp)
//                        )
//                    }
//                }
//            }
//            is LoadState.Error -> {
//                item {
//                    Text(
//                        text = "Error"
//                    )
//                }
//            }
//        }
    }
}