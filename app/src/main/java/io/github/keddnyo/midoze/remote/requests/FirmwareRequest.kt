package io.github.keddnyo.midoze.remote.requests

import android.util.Log
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.local.models.firmware.Device
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

suspend fun getFirmware(
    device: Device
): Firmware? {
    val client = HttpClient()
    val targetHost = "api-mifit-us2.huami.com"
    val request = client.get {
        url {
            protocol = URLProtocol.HTTPS
            host = targetHost
            appendPathSegments("devices", "ALL", "hasNewVersion")
            parameter("productId", "0")
            parameter("vendorSource", "0")
            parameter("resourceVersion", "0")
            parameter("firmwareFlag", "0")
            parameter("vendorId", "0")
            parameter("resourceFlag", "0")
            parameter("productionSource", device.productionSource)
            parameter("userid", "0")
            parameter("userId", "0")
            parameter("deviceSource", device.deviceSource)
            parameter("fontVersion", "0")
            parameter("diagnosticCode", "0")
            parameter("fontFlag", "0")
            parameter("appVersion", device.application.appVersion)
            parameter("appid", "0")
            parameter("callid", "0")
            parameter("channel", "0")
            parameter("country", device.region.country)
            parameter("cv", "0")
            parameter("device", "0")
            parameter("deviceType", "ALL")
            parameter("device_type", "0")
            parameter("firmwareVersion", "0")
            parameter("hardwareVersion", "0")
            parameter("lang", device.region.language)
            parameter("support8Bytes", "0")
            parameter("timezone", "0")
            parameter("v", "0")
//            parameter("gpsVersion", "0")
//            parameter("baseResourceVersion", "0")
        }
        headers {
            append("Host", targetHost)
            append("Hm-Privacy-Diagnostics", "false")
            append("Country", device.region.country)
            append("Appplatform", "android_phone")
            append("Hm-Privacy-Ceip", "false")
            append("X-Request-Id", "0")
            append("Timezone", "0")
            append("Channel", "0")
            append("User-Agent", "0")
            append("Cv", "2.0")
            append("Appname", device.application.instance.name)
            append("V", "0")
            append("Apptoken", "0")
            append("Lang", device.region.language)
            append("Connection", "Keep-Alive")
            append("Accept-Encoding", "gzip, deflate")
        }
    }

    if (request.status.value != 200) return null

    val response = try {
        request.bodyAsText()
    } catch (e: Exception) {
        "{}"
    }

    val firmwareData = JSONObject(response)

    if (!firmwareData.has("firmwareVersion")) {
        return null
    } else {
        Log.d(
            "Firmware-request",
            "deviceSource: ${device.deviceSource}, productionSource: ${device.productionSource}, status: ${request.status.value}, Firmware version: ${firmwareData.getString("firmwareVersion")}"
        )
    }

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
        changeLog = get("changeLog")?.substringBefore("###summary###"),
        buildTime = get("buildTime")?.getDate(),
    )
}

private fun String.getDate(): String {
    val dateFormat = SimpleDateFormat("yyyyMMddhhmm", Locale.getDefault())
    val firmwareDateFormatted = dateFormat.parse(this)
    return DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
        .format(firmwareDateFormatted!!)
}