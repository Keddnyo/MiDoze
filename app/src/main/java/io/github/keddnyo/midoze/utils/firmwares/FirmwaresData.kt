package io.github.keddnyo.midoze.utils.firmwares

data class FirmwaresData(
    val deviceName: String,
    val deviceIcon: Int,
    val firmwareReleaseDate: String,
    val firmwareChangelog: String,
    val deviceSource: Int,
    val productionSource: Int,
    val appName: String,
    val appVersion: String
)
