package io.github.keddnyo.midoze.local.devices

import org.json.JSONObject

data class DeviceData(
    val icon: Int,
    val name: String,
    val firmware: JSONObject,
    val firmwareVersion: String,
    val buildTime: String,
    val changeLog: String,
    val deviceSource: String,
    val productionSource: String,
    val appName: String,
    val appVersion: String
)
