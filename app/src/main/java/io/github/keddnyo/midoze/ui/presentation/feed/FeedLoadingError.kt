package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.keddnyo.midoze.internal.CardContentOffset

@Composable
fun FeedLoadingError(message: String) {
    Card(
        modifier = Modifier
            .widthIn(min = 0.dp, max = 600.dp)
            .padding(all = CardContentOffset),
        elevation = CardDefaults.outlinedCardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CardContentOffset),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                modifier = Modifier
                    .size(size = 64.dp)
            )
            Spacer(modifier = Modifier.width(CardContentOffset))
            Text(
                text = "Something went wrong.",
                modifier = Modifier
                    .padding(all = CardContentOffset),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            modifier = Modifier
                .padding(start = CardContentOffset, end = CardContentOffset),
            thickness = 0.5.dp,
            color = Color.Gray
        )
        Text(
            modifier = Modifier
                .padding(CardContentOffset),
            text = message,
            style = TextStyle(
                fontSize = 16.sp
            )
        )
    }
}