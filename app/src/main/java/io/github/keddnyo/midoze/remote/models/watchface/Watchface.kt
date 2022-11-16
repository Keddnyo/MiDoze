package io.github.keddnyo.midoze.remote.models.watchface

import androidx.compose.ui.graphics.ImageBitmap

data class Watchface(
    val tabName: String,
    val title: String,
    val preview: ImageBitmap,
    val watchfaceLink: String
)