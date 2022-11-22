package io.github.keddnyo.midoze.remote.requests

import org.json.JSONObject
import java.io.InputStream
import java.net.URL

fun StringBuilder.toURL() =
    URL(toString())

fun InputStream.getJsonResponse() =
    JSONObject(bufferedReader().readText())

fun JSONObject.getStringOrNull(item: String) =
    if (has(item)) {
        getString(item)
    } else {
        null
    }