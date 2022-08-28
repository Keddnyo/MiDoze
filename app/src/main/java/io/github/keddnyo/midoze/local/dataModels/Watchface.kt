package io.github.keddnyo.midoze.local.dataModels

import android.graphics.Bitmap

data class Watchface(
    val title: String,
    val preview: Bitmap,
    val url: String
)