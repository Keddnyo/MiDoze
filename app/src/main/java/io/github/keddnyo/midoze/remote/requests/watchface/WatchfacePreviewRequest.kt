package io.github.keddnyo.midoze.remote.requests.watchface

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.net.URL

fun String.getImage(): ImageBitmap {
    val url = URL(this)
    val byteArray = url.openConnection().inputStream.readBytes()
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return bitmap.asImageBitmap()
}