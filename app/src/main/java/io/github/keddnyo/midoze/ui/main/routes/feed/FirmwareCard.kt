package io.github.keddnyo.midoze.ui.main.routes.feed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
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
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

@Composable
fun FirmwarePostCard(
    firmware: Firmware?
) {
    firmware?.run {
        Card(
            modifier = Modifier
                .widthIn(min = 0.dp, max = 700.dp)
                .padding(all = 10.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp),
                ),
            elevation = CardDefaults.outlinedCardElevation(3.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.amazfit_band_7),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size = 64.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(50.dp),
                        )
                        .border(
                            BorderStroke(
                                width = 0.5.dp,
                                color = Color.Gray,
                            ),
                            shape = RoundedCornerShape(50.dp),
                        )
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Unknown device",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    firmwareVersion?.let { firmwareVersion ->
                        Text(
                            text = "Firmware: $firmwareVersion",
                            style = TextStyle(
                                fontSize = 14.sp,
                            ),
                        )
                    }
                    buildTime?.let { buildTime ->
                        Text(
                            text = buildTime,
                            style = TextStyle(
                                fontSize = 14.sp,
                            ),
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1F))
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null
                    )
                }
            }
            Divider(
                thickness = 0.5.dp,
                color = Color.Gray
            )
            changeLog?.let {
                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    text = changeLog,
                    style = TextStyle(
                        fontSize = 14.sp,
                    ),
                )
            }
        }
    }
}