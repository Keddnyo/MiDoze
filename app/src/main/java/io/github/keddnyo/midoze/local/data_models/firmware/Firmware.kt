package io.github.keddnyo.midoze.local.data_models.firmware

data class Firmware(
    val firmwareVersion: String?,
    val firmwareUrl: String?,
    val resourceVersion: String?,
    val resourceUrl: String?,
    val baseResourceVersion: String?,
    val baseResourceUrl: String?,
    val fontVersion: String?,
    val fontUrl: String?,
    val gpsVersion: String?,
    val gpsUrl: String?,
    val lang: String?,
    val changeLog: String?,
)