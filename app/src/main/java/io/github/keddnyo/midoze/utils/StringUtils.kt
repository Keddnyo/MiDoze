package io.github.keddnyo.midoze.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class StringUtils {
    fun getChangelogFixed(changeLog: String): String {
        var c = changeLog.substringBefore('#')
        c = c.replace(";", "")
        return c
    }

    fun getExtrasFixed(string: String): String {
        return string.replace("\\/", "/")
    }

    @SuppressLint("SimpleDateFormat")
    fun getLocaleFirmwareDate(firmwareDate: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Language().getCurrent())
        val date = format.parse(firmwareDate)
        return date?.toLocaleString()
            .toString()
            .replace("12:00:00 AM", "")
            .replace("00:00:00", "")
            .replace("0:00:00", "")
            .trim()
    }
}