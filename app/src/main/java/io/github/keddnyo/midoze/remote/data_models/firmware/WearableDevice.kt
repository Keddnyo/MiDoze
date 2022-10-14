package io.github.keddnyo.midoze.remote.data_models.firmware

data class WearableDevice(
    val deviceSource: String,
    val productionSource: String,
    val region: Region,
    val application: Application,
)