package io.github.keddnyo.midoze.local.dataModels

import android.graphics.Bitmap
import java.net.URL

data class WatchfaceStack(
    val title: String,
    val stack: ArrayList<Watchface>
)