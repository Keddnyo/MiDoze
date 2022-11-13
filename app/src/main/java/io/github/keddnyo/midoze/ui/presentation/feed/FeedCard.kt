package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

@Composable
fun FeedCard(
    firmware: Firmware?
) {
    firmware?.run {
        val offset = 15.dp

        Card(
            modifier = Modifier
                .widthIn(min = 0.dp, max = 600.dp)
                .padding(all = offset),
            elevation = CardDefaults.outlinedCardElevation(2.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(offset),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val cornerShape = RoundedCornerShape(50.dp)

                Image(
                    painter = painterResource(
                        device.devicePreview
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 64.dp)
                        .background(
                            color = Color.White,
                            shape = cornerShape
                        )
                        .border(
                            BorderStroke(
                                width = 0.5.dp,
                                color = Color.Gray,
                            ),
                            shape = cornerShape
                        )
                        .padding(10.dp),
                )
                Spacer(modifier = Modifier.width(offset))
                Column {
                    Text(
                        text = device.deviceName,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    firmwareVersion?.let { firmwareVersion ->
                        Text(
                            text = "FW: $firmwareVersion",
                            style = TextStyle(
                                fontSize = 14.sp
                            )
                        )
                    }
                    resourceVersion?.let { resourceVersion ->
                        Text(
                            text = "RES: $resourceVersion",
                            style = TextStyle(
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
            changeLog?.let {
                Divider(
                    modifier = Modifier
                        .padding(start = offset, end = offset),
                    thickness = 0.5.dp,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier
                        .padding(offset),
                    text = changeLog,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}