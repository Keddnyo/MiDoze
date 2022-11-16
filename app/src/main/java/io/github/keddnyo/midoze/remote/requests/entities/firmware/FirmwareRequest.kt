package io.github.keddnyo.midoze.remote.requests.entities.firmware

import io.github.keddnyo.midoze.local.models.firmware.FirmwareWearable
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.utils.getJsonResponse
import io.github.keddnyo.midoze.utils.getStringOrNull
import io.github.keddnyo.midoze.utils.toURL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getFirmware(
    host: String,
    firmwareWearable: FirmwareWearable
): Firmware? {

    val response = withContext(Dispatchers.IO) {

        val deviceSource = firmwareWearable.deviceSource
        val productionSource = firmwareWearable.productionSource
        val appVersion = firmwareWearable.application.appVersion
        val appName = firmwareWearable.application.appName

        StringBuilder()
            .append("https")
            .append("://")
            .append(host)
            .append("/devices/ALL/hasNewVersion?")
            .append("deviceSource=$deviceSource&")
            .append("productionSource=$productionSource&")
            .append("appVersion=$appVersion&")
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
                setRequestProperty("Appname", appName)
                setRequestProperty("Apptoken", "0")
                setRequestProperty("Lang", "0")
                setRequestProperty("Connection", "Keep-Alive")
                setRequestProperty("Accept-Encoding", "identity")
                connectTimeout = 5000
                readTimeout = 5000
                inputStream
            }
            .getJsonResponse()
    }

    if (!response.has("firmwareVersion")) return null

    response.run {
        return Firmware(
            firmwareWearable = firmwareWearable,
            firmwareVersion = getStringOrNull("firmwareVersion"),
            firmwareUrl = getStringOrNull("firmwareUrl"),
            resourceVersion = getStringOrNull("resourceVersion"),
            resourceUrl = getStringOrNull("resourceUrl"),
            baseResourceVersion = getStringOrNull("baseResourceVersion"),
            baseResourceUrl = getStringOrNull("baseResourceUrl"),
            fontVersion = getStringOrNull("fontVersion"),
            fontUrl = getStringOrNull("fontUrl"),
            gpsVersion = getStringOrNull("gpsVersion"),
            gpsUrl = getStringOrNull("gpsUrl"),
            changeLog = getStringOrNull("changeLog")
                ?.substringAfterLast("###summary###")
                ?.trim(),
        )
    }

}