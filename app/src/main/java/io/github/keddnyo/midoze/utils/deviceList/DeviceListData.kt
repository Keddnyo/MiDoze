package io.github.keddnyo.midoze.utils.deviceList

data class DeviceListData(
    val deviceName: String,
    val deviceIcon: Int,
    val firmwareVersion: String,
    val firmwareReleaseDate: String,
    val firmwareChangelog: String,
)
