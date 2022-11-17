package io.github.keddnyo.midoze.remote.models.watchface

import androidx.compose.ui.graphics.ImageBitmap
import io.github.keddnyo.midoze.local.models.watchface.WatchfaceWearable

data class WatchfaceList(
    val device: WatchfaceWearable,
    val preview: ImageBitmap,
    val watchfaceList: ArrayList<Watchface>
)