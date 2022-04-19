package io.github.keddnyo.midoze.utils.deviceList

data class DeviceListData(
    val deviceName: String,
    val deviceIcon: Int,
    val firmwareReleaseDate: String,
    val firmwareChangelog: String,
    val deviceIndex: Int
)
