package io.github.keddnyo.midoze.local.dataModels

import org.json.JSONObject

class Firmware {
    data class Device(
        val name: String,
        val preview: Int
    )

    data class Application(
        val name: String,
        val version: String?
    )

    data class Region(
        val country: String,
        val language: String
    )

    data class Wearable(
        val deviceSource: String,
        val productionSource: String,
        val application: Application,
        val region: Region
    )

    data class WearableStack(
        val name: String,
        val wearableStack: ArrayList<Wearable>
    )

    data class FirmwareData(
        val device: Device,
        val wearable: Wearable,
        val firmwareData: JSONObject
    )

    data class FirmwareDataArray(
        val name: String,
        val firmwareData: ArrayList<FirmwareData>
    )
}