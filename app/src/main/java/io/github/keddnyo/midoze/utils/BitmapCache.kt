package io.github.keddnyo.midoze.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.preference.PreferenceManager
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder

class BitmapCache(val context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = prefs.edit()

    fun encode(deviceName: String, dialName: String, bitmap: Bitmap) {
        ByteArrayOutputStream().let { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
            val byteArray = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
            editor.putString(getPreferenceName(deviceName, dialName), byteArray).apply()
        }
    }

    fun decode(deviceName: String, dialName: String): Bitmap? {
        val preferenceValue = prefs.getString(getPreferenceName(deviceName, dialName), "")

        val byteArray: ByteArray = Base64.decode(preferenceValue, Base64.DEFAULT)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = false
            inPreferredConfig = Bitmap.Config.RGB_565
            inDither = true
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
    }

    private fun getPreferenceName(deviceName: String, dialName: String): String {
        return StringBuilder()
            .append(deviceName)
            .append("_")
            .append(dialName)
            .toString()
    }
}