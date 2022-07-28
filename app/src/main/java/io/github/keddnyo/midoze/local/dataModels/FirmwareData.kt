package io.github.keddnyo.midoze.local.dataModels

import org.json.JSONObject

data class FirmwareData(
    val device: Device,
    val wearable: Wearable,
    val firmware: JSONObject,
    val firmwareVersion: String,
    val changeLog: String
)
