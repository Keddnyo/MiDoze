package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.local.data_models.firmware.Firmware
import io.github.keddnyo.midoze.local.data_models.firmware.utils.getFirmwareProperties
import io.github.keddnyo.midoze.remote.data_models.firmware.WearableDevice
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun getFirmware(
    wearableDevice: WearableDevice
): Firmware? {
    val client = HttpClient()
    val targetHost = "api-mifit-us2.huami.com"

    wearableDevice.run {
        client.get {
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
                parameter("productionSource", productionSource)
                parameter("userid", "0")
                parameter("userId", "0")
                parameter("deviceSource", deviceSource)
                parameter("fontVersion", "0")
                parameter("fontFlag", "0")
                parameter("appVersion", application.appVersion)
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
                append("country", region.country)
                append("appplatform", "android_phone")
                append("hm-privacy-ceip", "0")
                append("x-request-id", "0")
                append("timezone", "0")
                append("channel", "0")
                append("user-agent", "0")
                append("cv", "0")
                append("appname", application.appName)
                append("v", "0")
                append("apptoken", "0")
                append("lang", region.lang)
                append("Host", targetHost)
                append("Connection", "Keep-Alive")
                append("accept-encoding", "gzip")
                append("accept", "*/*")
            }
        }.bodyAsText().let { response ->
            return getFirmwareProperties(response)
        }
    }
}