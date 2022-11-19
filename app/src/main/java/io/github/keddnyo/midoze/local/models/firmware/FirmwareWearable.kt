package io.github.keddnyo.midoze.local.models.firmware

data class FirmwareWearable(
    val deviceName: String,
    val devicePreview: Int,
    val deviceSource: Int,
    val productionSource: Int,
    val firmwareApplication: FirmwareApplication
)