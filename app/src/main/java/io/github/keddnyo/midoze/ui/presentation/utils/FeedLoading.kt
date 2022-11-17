package io.github.keddnyo.midoze.ui.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(56.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}