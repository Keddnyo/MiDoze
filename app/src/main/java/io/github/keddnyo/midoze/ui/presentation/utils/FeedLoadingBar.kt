package io.github.keddnyo.midoze.ui.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.keddnyo.midoze.internal.CardContentOffset
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier
            .padding(CardContentOffset + 5.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}