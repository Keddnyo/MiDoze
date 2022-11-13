package io.github.keddnyo.midoze.utils

import android.net.Uri

fun Uri.getFileName(): String {
    val string = this.toString()
    return string.substring(string.lastIndexOf("/" + 1))
}