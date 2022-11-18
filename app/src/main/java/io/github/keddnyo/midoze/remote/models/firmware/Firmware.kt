package io.github.keddnyo.midoze.remote.models.firmware

import io.github.keddnyo.midoze.local.models.firmware.FirmwareWearable

data class Firmware(
    val wearable: FirmwareWearable,
    val firmwareEntities: ArrayList<FirmwareEntity>,
    val changeLog: String? = null
)