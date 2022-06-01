package io.github.keddnyo.midoze.utils.firmwares

data class FirmwaresData(
    val deviceName: String,
    val deviceIcon: Int,
    val firmwareReleaseDate: String,
    val firmwareChangelog: String,
    val deviceIndex: Int
)
