package io.github.keddnyo.midoze.utils

import android.content.Context
import android.util.DisplayMetrics
import java.text.DateFormat
import java.text.SimpleDateFormat

class Display {
    fun getGridLayoutIndex(
        context: Context,
        columnWidthDp: Int,
    ): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp.toString().toFloat() + 0.5).toInt()
    }

    fun bytesToHumanReadableSize(bytes: Double) = when {
        bytes >= 1 shl 30 -> "%.1f GB".format(bytes / (1 shl 30))
        bytes >= 1 shl 20 -> "%.1f MB".format(bytes / (1 shl 20))
        bytes >= 1 shl 10 -> "%.0f kB".format(bytes / (1 shl 10))
        else -> "$bytes bytes"
    }

    fun getLocaleFirmwareDate(firmwareDate: String): String {
        val dateFormat = SimpleDateFormat("yyyyMMddhhmm", DozeLocale().currentLocale)
        val firmwareDateFormatted = dateFormat.parse(firmwareDate)
        return DateFormat.getDateInstance(DateFormat.MEDIUM, DozeLocale().currentLocale)
            .format(firmwareDateFormatted!!)
    }
}