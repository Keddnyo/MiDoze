package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.internal.CardContentOffset

@Composable
fun FeedCard(
    icon: Int = R.drawable.unknown,
    title: String,
    iconButton: ImageVector = Icons.Default.Refresh,
    subtitle1: String? = null,
    subtitle2: String? = null,
    summary: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(min = 0.dp, max = 600.dp)
            .padding(all = CardContentOffset),
        elevation = CardDefaults.outlinedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CardContentOffset),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val cornerShape = RoundedCornerShape(50.dp)
            
            Image(
                painterResource(id = icon),
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
            Spacer(modifier = Modifier.width(CardContentOffset))
            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                subtitle1?.run {
                    Text(
                        text = this,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
                subtitle2?.run {
                    Text(
                        text = this,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(1.0F)
            )
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = iconButton,
                    contentDescription =""
                )
            }
        }
        summary?.run {
            Divider(
                modifier = Modifier
                    .padding(start = CardContentOffset, end = CardContentOffset),
                thickness = 0.5.dp,
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .padding(CardContentOffset),
                text = this,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
    }
}