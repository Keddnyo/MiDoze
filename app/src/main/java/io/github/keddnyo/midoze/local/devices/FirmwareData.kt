package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.local.apps.Application
import org.json.JSONObject

data class FirmwareData(
    val wearable: Wearable,
    val application: Application,
    val firmware: JSONObject,
    val firmwareVersion: String,
    val buildTime: String,
    val changeLog: String,
    val deviceSource: String,
    val productionSource: String
)
