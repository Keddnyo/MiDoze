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
        .append("country=${device.region.country}&")
        .append("cv=0&")
        .append("device=0&")
        .append("deviceType=ALL&")
        .append("device_type=0&")
        .append("firmwareVersion=0&")
        .append("hardwareVersion=0&")
        .append("lang=${device.region.language}&")
        .append("support8Bytes=0&")
        .append("timezone=0&")
        .append("v=0")
        .toURL()
        .openConnection()
        .run {
            setRequestProperty("Host", host)
            setRequestProperty("Hm-Privacy-Diagnostics", "false")
            setRequestProperty("Country", device.region.country)
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
            setRequestProperty("Lang", device.region.language)
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