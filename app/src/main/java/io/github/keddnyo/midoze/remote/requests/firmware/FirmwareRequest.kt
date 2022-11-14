package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.local.models.firmware.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

suspend fun getFirmware(
    host: String,
    device: Device
): Firmware? = withContext(Dispatchers.IO) {
    val request = StringBuilder()
        .append("https")
        .append("://")
        .append(host)
        .append("/devices/ALL/hasNewVersion?")
        .append("deviceSource=${device.deviceSource}&")
        .append("productionSource=${device.productionSource}&")
        .append("appVersion=${device.application.appVersion.appVersion}&")
        .append("firmwareVersion=0&")
        .append("resourceVersion=0&")
        .append("baseResourceVersion=0&")
        .append("gpsVersion=0&")
        .append("fontVersion=0&")
        .append("deviceType=ALL&")
        .append("userId=0&")
        .append("support8Bytes=true&")
        .toURL()
        .openConnection()
        .run {
            setRequestProperty("Appplatform", "android_phone")
            setRequestProperty("Appname", device.application.instance.appName)
            setRequestProperty("Apptoken", "0")
            setRequestProperty("Lang", "0")
            setRequestProperty("Connection", "Keep-Alive")
            setRequestProperty("Accept-Encoding", "identity")

            // Return server response as text
            inputStream
        }

    val response = request.getContent()

    val firmwareData = JSONObject(response)

    // Return null if response hasn't firmwareVersion field
    if (!firmwareData.has("firmwareVersion")) return@withContext null

    // Return field text or null by key
    fun getOrNull(item: String) =
        if (firmwareData.has(item)) {
            firmwareData.getString(item)
        } else {
            null
        }

    // Return firmware instance
    return@withContext Firmware(
        device = device,
        firmwareVersion = getOrNull("firmwareVersion"),
        firmwareUrl = getOrNull("firmwareUrl"),
        resourceVersion = getOrNull("resourceVersion"),
        resourceUrl = getOrNull("resourceUrl"),
        baseResourceVersion = getOrNull("baseResourceVersion"),
        baseResourceUrl = getOrNull("baseResourceUrl"),
        fontVersion = getOrNull("fontVersion"),
        fontUrl = getOrNull("fontUrl"),
        gpsVersion = getOrNull("gpsVersion"),
        gpsUrl = getOrNull("gpsUrl"),
        changeLog = getOrNull("changeLog")?.substringBefore("###summary###")?.trim(),
        buildTime = getOrNull("buildTime")?.getDate(),
    )
}

private fun StringBuilder.toURL() = URL(this.toString())

private fun InputStream.getContent() = this.bufferedReader().readText()

private fun String.getDate(): String {
    val dateFormat = SimpleDateFormat("yyyyMMddhhmm", Locale.getDefault())
    val firmwareDateFormatted = dateFormat.parse(this)
    return DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
        .format(firmwareDateFormatted!!)
}