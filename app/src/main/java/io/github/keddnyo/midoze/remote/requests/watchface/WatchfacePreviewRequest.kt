package io.github.keddnyo.midoze.remote.requests.watchface

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

fun URL.getImage(): Bitmap? {
    val byteArray = openConnection().inputStream.readBytes()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}