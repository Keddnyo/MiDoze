package io.github.keddnyo.midoze.remote.models.firmware

data class FirmwareDownload(
    val fileType: FirmwareType,
    val fileVersion: String,
    val address: String
)