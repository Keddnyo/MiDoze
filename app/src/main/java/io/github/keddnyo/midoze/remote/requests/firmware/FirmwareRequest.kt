package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.utils.getJsonResponse
import io.github.keddnyo.midoze.utils.getOrNull
import io.github.keddnyo.midoze.utils.toURL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getFirmware(
    host: String,
    device: Device
): Firmware? {

    val response = withContext(Dispatchers.IO) {
        StringBuilder()
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
                inputStream
            }
            .getJsonResponse()
    }

    if (!response.has("firmwareVersion")) return null

    response.run {
        return Firmware(
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
            changeLog = getOrNull("changeLog")
                ?.substringAfterLast("###summary###")
                ?.trim(),
        )
    }

}