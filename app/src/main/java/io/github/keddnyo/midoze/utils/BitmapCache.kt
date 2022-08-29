package io.github.keddnyo.midoze.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class BitmapCache {
    fun encode(bitmap: Bitmap): String {
        ByteArrayOutputStream().let { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
        }
    }

    fun decode(string: String): Bitmap? {
        val byteArray: ByteArray = Base64.decode(string, Base64.DEFAULT)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = false
            inPreferredConfig = Bitmap.Config.RGB_565
            inDither = true
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
    }
}