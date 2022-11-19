package io.github.keddnyo.midoze.ui.presentation.dials

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import coil.compose.AsyncImage
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.internal.CardContentOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialCard(
    title: String,
    subtitle: String? = null,
    preview: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(min = 0.dp, max = 600.dp)
            .padding(all = CardContentOffset),
        elevation = CardDefaults.outlinedCardElevation(2.dp),
        border = BorderStroke(
            0.5.dp,
            Color.Gray
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CardContentOffset),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            preview?.let {
                AsyncImage(
                    model = preview,
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(100.dp),
                    placeholder = painterResource(id = R.drawable.unknown)
                )
                Spacer(
                    modifier = Modifier
                        .width(CardContentOffset)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Divider(
                    modifier = Modifier
                        .padding(CardContentOffset),
                    thickness = 0.5.dp,
                    color = Color.Gray
                )
                subtitle?.run {
                    Text(
                        text = this,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}