package io.github.keddnyo.midoze.remote.models.firmware

import io.github.keddnyo.midoze.local.models.firmware.FirmwareWearable

data class Firmware (
    val firmwareWearable: FirmwareWearable,
    val firmwareVersion: String?,
    val firmwareUrl: String?,
    val resourceVersion: String? = null,
    val resourceUrl: String? = null,
    val baseResourceVersion: String? = null,
    val baseResourceUrl: String? = null,
    val fontVersion: String? = null,
    val fontUrl: String? = null,
    val gpsVersion: String? = null,
    val gpsUrl: String? = null,
    val changeLog: String? = null
)