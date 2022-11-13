package io.github.keddnyo.midoze.remote.models.firmware

abstract class FirmwareType(
    val label: String
) {
    object Firmware : FirmwareType(
        label = "Firmware"
    )
    object Resource : FirmwareType(
        label = "Resource"
    )
    object BaseResource : FirmwareType(
        label = "BaseResource"
    )
    object Font : FirmwareType(
        label = "Font"
    )
    object Gps : FirmwareType(
        label = "Gps"
    )
}