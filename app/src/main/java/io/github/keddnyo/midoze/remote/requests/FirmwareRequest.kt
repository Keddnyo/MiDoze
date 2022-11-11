package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.local.models.firmware.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

suspend fun getFirmware(
    device: Device
): Firmware? {
    val host = "api-mifit-us2.huami.com"

    val request = StringBuilder()
        .append("https")
        .append("://")
        .append(host)
        .append("/devices/ALL/hasNewVersion?")
        .append("productId=0&")
        .append("vendorSource=0&")
        .append("resourceVersion=0&")
        .append("firmwareFlag=0&")
        .append("vendorId=0&")
        .append("resourceFlag=0&")
        .append("productionSource=${device.productionSource}&")
        .append("userid=0&")
        .append("userId=0&")
        .append("deviceSource=${device.deviceSource}&")
        .append("fontVersion=0&")
        .append("diagnosticCode=0&")
        .append("fontFlag=0&")
        .append("appVersion=${device.application.appVersion.appVersion}&")
        .append("appid=0&")
        .append("callid=0&")
        .append("channel=0&")
        .append("country=0&")
        .append("cv=0&")
        .append("device=0&")
        .append("deviceType=ALL&")
        .append("device_type=0&")
        .append("firmwareVersion=0&")
        .append("hardwareVersion=0&")
        .append("lang=0&")
        .append("support8Bytes=0&")
        .append("timezone=0&")
        .append("v=0")
        .toURL()
        .openConnection()
        .run {
            setRequestProperty("Host", host)
            setRequestProperty("Hm-Privacy-Diagnostics", "false")
            setRequestProperty("Country", "0")
            setRequestProperty("Appplatform", "android_phone")
            setRequestProperty("Hm-Privacy-Ceip", "false")
            setRequestProperty("X-Request-Id", "0")
            setRequestProperty("Timezone", "0")
            setRequestProperty("Channel", "0")
            setRequestProperty("User-Agent", "0")
            setRequestProperty("Cv", "0")
            setRequestProperty("Appname", device.application.instance.appName)
            setRequestProperty("V", "0")
            setRequestProperty("Apptoken", "0")
            setRequestProperty("Lang", "0")
            setRequestProperty("Connection", "Keep-Alive")
            setRequestProperty("Accept-Encoding", "identity")

            withContext(Dispatchers.IO) {
                inputStream
            }
        }

    val response = request.getContent()

    val firmwareData = JSONObject(response)

    if (!firmwareData.has("firmwareVersion")) return null

    fun get(item: String) =
        if (firmwareData.has(item)) {
            firmwareData.getString(item)
        } else {
            null
        }

    return Firmware(
        device = device,
        firmwareVersion = get("firmwareVersion"),
        firmwareUrl = get("firmwareUrl"),
        resourceVersion = get("resourceVersion"),
        resourceUrl = get("resourceUrl"),
        baseResourceVersion = get("baseResourceVersion"),
        baseResourceUrl = get("baseResourceUrl"),
        fontVersion = get("fontVersion"),
        fontUrl = get("fontUrl"),
        gpsVersion = get("gpsVersion"),
        gpsUrl = get("gpsUrl"),
        changeLog = get("changeLog")?.substringBefore("###summary###")?.trim(),
        buildTime = get("buildTime")?.getDate(),
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