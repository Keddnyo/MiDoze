package io.github.keddnyo.midoze.local.data_models.firmware.utils

import io.github.keddnyo.midoze.local.data_models.firmware.Firmware
import org.json.JSONObject

fun getFirmwareProperties(
    response: String
): Firmware? {
    val firmware = JSONObject(response)

    if (!firmware.has("firmwareVersion")) {
        return null
    }

    return firmware.run {
        Firmware(
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
            lang = getOrNull("lang"),
            changeLog = getOrNull("changeLog"),
        )
    }
}

fun JSONObject.getOrNull(
    property: String
): String? {
    this.has(property).let { isPropertyExists ->
        return if (isPropertyExists) {
            this.getString(property)
        } else {
            null
        }
    }
}