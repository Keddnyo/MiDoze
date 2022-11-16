package io.github.keddnyo.midoze.ui.presentation.dial

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.keddnyo.midoze.internal.CardContentOffset
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel
import io.github.keddnyo.midoze.remote.requests.download.downloadWatchface

@Composable
fun DialRoute(
    viewModel: WatchfaceViewModel
) {

    val context = LocalContext.current
    val watchfaceList = viewModel.watchfaceList.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        watchfaceList.run {
            items(this) { watchfaceList ->
                watchfaceList?.watchfaceList?.let {
                    Card(
                        modifier = Modifier
                            .widthIn(min = 0.dp, max = 600.dp)
                            .padding(all = CardContentOffset),
                        elevation = CardDefaults.outlinedCardElevation(2.dp),
                        border = BorderStroke(
                            0.5.dp,
                            Color.Gray
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(CardContentOffset)
                        ) {
                            Image(
                                bitmap = it[0].preview,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(150.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(CardContentOffset)
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = it[0].title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = it[0].tabName,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                                Divider(
                                    modifier = Modifier
                                        .padding(CardContentOffset),
                                    thickness = 0.5.dp,
                                    color = Color.Gray
                                )
                                Button(
                                    onClick = {
                                        downloadWatchface(
                                            context = context,
                                            watchface = it[0]
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Download"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}