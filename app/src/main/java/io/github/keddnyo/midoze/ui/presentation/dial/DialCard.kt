package io.github.keddnyo.midoze.ui.presentation.dial

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.keddnyo.midoze.internal.CardContentOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialCard(
    preview: ImageBitmap,
    title: String,
    subtitle: String? = null,
    summary: String? = null,
    onClickAction: () -> Unit
) {
    Card(
        onClick = onClickAction,
        modifier = Modifier
            .widthIn(min = 0.dp, max = 600.dp)
            .padding(all = CardContentOffset),
        elevation = CardDefaults.outlinedCardElevation(2.dp),
        border = BorderStroke(
            0.5.dp,
            Color.Gray
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CardContentOffset)
        ) {
            Image(
                bitmap = preview,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(130.dp),
                alignment = Alignment.Center
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
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                subtitle?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Divider(
                    modifier = Modifier
                        .padding(CardContentOffset),
                    thickness = 0.5.dp,
                    color = Color.Gray
                )
                summary?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}