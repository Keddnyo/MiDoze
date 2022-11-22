package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.local.Wearable
import io.github.keddnyo.midoze.local.wearables
import io.github.keddnyo.midoze.remote.Firmware
import io.github.keddnyo.midoze.remote.FirmwareEntity
import io.github.keddnyo.midoze.remote.FirmwareLabel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getFirmware(
    host: String,
    wearable: Wearable
): Firmware? {

    val response = withContext(Dispatchers.IO) {

        val deviceSource = wearable.deviceSource
        val productionSource = wearable.productionSource
        val appVersion = wearable.application.appVersion
        val appName = wearable.application.appName

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

    val firmwareVersion = response.getStringOrNull("firmwareVersion")
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
            wearable = wearable,
            firmwareEntities = firmwareEntityArray,
            changeLog = getStringOrNull("changeLog")
                ?.substringAfterLast("###summary###")
                ?.trim(),
        )
    }

}

suspend fun getFirmwareList(
    i: Int
): ArrayList<Firmware> {
    val array = arrayListOf<Firmware>()

    val routeArray = arrayOf(
        "api.amazfit.com",
        "api-mifit-us2.huami.com",
        "api-mifit-ru.huami.com"
    )

    array.run {
        routeArray.forEach { host ->

            getFirmware(
                host = host,
                wearable = wearables[i].run {
                    Wearable(
                        deviceName, devicePreview, deviceSource, productionSource, application
                    )
                }
            )?.apply {
                add(this)
                return@run
            }
        }
    }

    return array
}