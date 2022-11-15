package io.github.keddnyo.midoze.ui.presentation.dial

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.keddnyo.midoze.internal.CardContentOffset
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel

@Composable
fun DialRoute(
    viewModel: WatchfaceViewModel
) {

    val watchfaceList = viewModel.watchfaceList

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        watchfaceList.forEach { watchface ->
            item {
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = watchface.preview,
                            contentDescription = null,
                            modifier = Modifier.size(256.dp)
                                .padding(CardContentOffset)
                        )
                        Text(
                            text = watchface.title,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }

}