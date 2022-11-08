package io.github.keddnyo.midoze.remote.requests

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
    val targetHost = "api.amazfit.com"
    val request: HttpResponse = client.get {
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
            parameter("fontFlag", "0")
            parameter("appVersion", device.application.appVersion)
            parameter("appid", "0")
            parameter("callid", "0")
            parameter("channel", "0")
            parameter("country", "0")
            parameter("cv", "0")
            parameter("device", "0")
            parameter("deviceType", "ALL")
            parameter("device_type", "android_phone")
            parameter("firmwareVersion", "0")
            parameter("hardwareVersion", "0")
            parameter("lang", "0")
            parameter("support8Bytes", "true")
            parameter("timezone", "0")
            parameter("v", "0")
            parameter("gpsVersion", "0")
            parameter("baseResourceVersion", "0")
        }
        headers {
            append("hm-privacy-diagnostics", "false")
            append("country", device.region.country)
            append("appplatform", "android_phone")
            append("hm-privacy-ceip", "0")
            append("x-request-id", "0")
            append("timezone", "0")
            append("channel", "0")
            append("user-agent", "0")
            append("cv", "0")
            append("appname", device.application.instance.name)
            append("v", "0")
            append("apptoken", "0")
            append("lang", device.region.language)
            append("Host", targetHost)
            append("Connection", "Keep-Alive")
            append("accept-encoding", "gzip")
            append("accept", "*/*")
        }
    }

    val response: String = try {
        request.bodyAsText()
    } catch (e: Exception) {
        null
    } ?: return null

    val firmwareData = JSONObject(response)

    fun get(item: String): String? {
        if (!firmwareData.has(item)) return null

        return firmwareData.getString(item)
    }

    if (!firmwareData.has("firmwareVersion")) return null

    val firmwareVersion = get("firmwareVersion")
    val resourceVersion = get("resourceVersion")
    val baseResourceVersion = get("baseResourceVersion")
    val fontVersion = get("fontVersion")
    val gpsVersion = get("gpsVersion")

    return Firmware(
        device = device,
        firmwareVersion = firmwareVersion,
        firmwareUrl = get("firmwareUrl"),
        resourceVersion = resourceVersion,
        resourceUrl = get("resourceUrl"),
        baseResourceVersion = baseResourceVersion,
        baseResourceUrl = get("baseResourceUrl"),
        fontVersion = fontVersion,
        fontUrl = get("fontUrl"),
        gpsVersion = gpsVersion,
        gpsUrl = get("gpsUrl"),
        changeLog = get("changeLog")?.substringBefore("###summary###") ?: "- Fixed some known issues.",
        buildTime = get("buildTime")?.getDate(),
    )
}

private fun String.getDate(): String {
    val dateFormat = SimpleDateFormat("yyyyMMddhhmm", Locale.getDefault())
    val firmwareDateFormatted = dateFormat.parse(this)
    return DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
        .format(firmwareDateFormatted!!)
}