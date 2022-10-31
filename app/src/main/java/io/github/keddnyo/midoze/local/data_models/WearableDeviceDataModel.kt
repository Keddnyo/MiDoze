package io.github.keddnyo.midoze.local.data_models

data class WearableDeviceDataModel(
    val deviceSource: Int,
    val productionSource: Int,
    val application: WearableApplicationDataModel,
    val region: WearableRegionDataModel,
)

data class WearableApplicationDataModel (
    val appName: String,
    val appVersion: String,
)

data class WearableRegionDataModel (
    val lang: String,
    val country: String,
)