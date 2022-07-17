package io.github.keddnyo.midoze.utils

import io.github.keddnyo.midoze.R
import java.text.DateFormat
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

    fun getLocalFirmwareDate(firmwareDate: String): String {
        val dateFormat = SimpleDateFormat("yyyyMMddhhmm", LanguageUtils().currentLocale)
        val firmwareDateFormatted = dateFormat.parse(firmwareDate)
        return DateFormat.getDateInstance(DateFormat.MEDIUM, LanguageUtils().currentLocale)
            .format(firmwareDateFormatted!!)
    }
}