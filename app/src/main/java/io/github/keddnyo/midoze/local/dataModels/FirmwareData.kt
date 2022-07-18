package io.github.keddnyo.midoze.local.dataModels

import org.json.JSONObject

data class FirmwareData(
    val wearable: Device,
    val application: Application,
    val firmware: JSONObject,
    val firmwareVersion: String,
    val changeLog: String,
    val deviceSource: String,
    val productionSource: String,
    val region: Region
)
