package io.github.keddnyo.midoze.remote.requests.entities.firmware

import io.github.keddnyo.midoze.local.models.firmware.FirmwareWearable
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareEntity
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareLabel
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

    val firmwareEntityArray: ArrayList<FirmwareEntity> = arrayListOf()

    val firmwareVersion = response.getStringOrNull("firmwareUrl")
    val firmwareUrl = response.getStringOrNull("firmwareUrl")
    val resourceVersion = response.getStringOrNull("resourceVersion")
    val resourceUrl = response.getStringOrNull("resourceUrl")
    val baseResourceVersion = response.getStringOrNull("baseResourceVersion")
    val baseResourceUrl = response.getStringOrNull("baseResourceUrl")
    val fontVersion = response.getStringOrNull("fontVersion")
    val fontUrl = response.getStringOrNull("fontUrl")
    val gpsVersion = response.getStringOrNull("gpsVersion")
    val gpsUrl = response.getStringOrNull("gpsUrl")

    if (firmwareVersion != null && firmwareUrl != null) {
        firmwareEntityArray.add(
            FirmwareEntity(
                firmwareVersion,
                firmwareUrl,
                FirmwareLabel.Firmware
            )
        )
    }
    if (resourceVersion != null && resourceUrl != null) {
        firmwareEntityArray.add(
            FirmwareEntity(
                resourceVersion,
                resourceUrl,
                FirmwareLabel.Resource
            )
        )
    }
    if (baseResourceVersion != null && baseResourceUrl != null) {
        firmwareEntityArray.add(
            FirmwareEntity(
                baseResourceVersion,
                baseResourceUrl,
                FirmwareLabel.BaseResource
            )
        )
    }
    if (fontVersion != null && fontUrl != null) {
        firmwareEntityArray.add(
            FirmwareEntity(
                fontVersion,
                fontUrl,
                FirmwareLabel.Font
            )
        )
    }
    if (gpsVersion != null && gpsUrl != null) {
        firmwareEntityArray.add(
            FirmwareEntity(
                gpsVersion,
                gpsUrl,
                FirmwareLabel.Gps
            )
        )
    }

    response.run {
        return Firmware(
            wearable = firmwareWearable,
            firmwareEntities = firmwareEntityArray,
            changeLog = getStringOrNull("changeLog")
                ?.substringAfterLast("###summary###")
                ?.trim(),
        )
    }

}