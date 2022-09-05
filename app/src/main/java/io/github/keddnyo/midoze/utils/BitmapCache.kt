package io.github.keddnyo.midoze.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder

class BitmapCache(val context: Context) {
    private fun getFileDir(deviceName: String, dialName: String): String {
        val cacheDir = context.cacheDir
        return StringBuilder()
            .append(cacheDir)
            .append(File.pathSeparator)
            .append(deviceName)
            .append(dialName)
            .toString()
    }

    private fun Bitmap.smallSize(): Bitmap {
        val maxSize = 600
        val outWidth: Int
        val outHeight: Int
        val inWidth = this.width
        val inHeight = this.height
        if (inWidth > maxSize || inHeight > maxSize) {
            if (inWidth > inHeight) {
                outWidth = maxSize
                outHeight = (inHeight * maxSize) / inWidth
            } else {
                outHeight = maxSize
                outWidth = (inWidth * maxSize) / inHeight
            }
        } else {
            outWidth = inWidth
            outHeight = inHeight
        }
        return Bitmap.createScaledBitmap(this, outWidth, outHeight, false)
    }

    fun encode(deviceName: String, dialName: String, bitmap: Bitmap) {
        try {
            val out = FileOutputStream(File(getFileDir(deviceName, dialName)))
            bitmap.smallSize().compress(Bitmap.CompressFormat.PNG, 0, out)
        } catch (e: Exception) {

        }
    }

    fun decode(deviceName: String, dialName: String): Bitmap? {
        return try {
            BitmapFactory.decodeFile(getFileDir(deviceName, dialName))
        } catch (e: Exception) {
            null
        }
    }
}