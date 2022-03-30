package io.github.keddnyo.midoze.utils

class StringUtils {
    fun getChangelogFixed(changeLog: String): String {
        var c = changeLog.substringBefore('#')
        c = c.replace(";", "")
        return c
    }
}