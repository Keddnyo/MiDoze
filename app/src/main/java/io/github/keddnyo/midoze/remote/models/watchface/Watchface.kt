package io.github.keddnyo.midoze.remote.models.watchface

import androidx.compose.ui.graphics.asImageBitmap
import io.github.keddnyo.midoze.remote.requests.watchface.getImage
import java.net.URL

data class Watchface(
    val tabName: String,
    val title: String,
    val preview: String,
    val watchfaceLink: String
) {
    val imagePreview =
        URL(preview).getImage()!!.asImageBitmap()

    val watchfaceUrl: URL
        get() = URL(watchfaceLink)
}