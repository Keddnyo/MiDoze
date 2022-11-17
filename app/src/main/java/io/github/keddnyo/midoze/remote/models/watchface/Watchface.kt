package io.github.keddnyo.midoze.remote.models.watchface

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

data class Watchface(
    val tabName: String,
    val title: String,
    val preview: String,
    val watchfaceLink: String
) {
    val watchfaceUrl: Uri
        get() = Uri.parse(watchfaceLink)

    val fileName = StringBuilder()
        .append(title)
        .append(".bin")
        .toString()
        .replace(" ", "_")
}