package io.github.keddnyo.midoze.local.dataModels

import android.graphics.Bitmap
import java.net.URL

data class Watchface(
    val title: String,
    val preview: Bitmap,
    val url: URL
)