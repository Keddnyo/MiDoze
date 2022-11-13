package io.github.keddnyo.midoze.remote.models.firmware

abstract class FirmwareType(
    val label: String,
    val extension: String
) {
    object Firmware : FirmwareType(
        label = "Firmware",
        extension = ".fw"
    )
    object Resource : FirmwareType(
        label = "Resource",
        extension = ".res"
    )
    object BaseResource : FirmwareType(
        label = "BaseResource",
        extension = ".res"
    )
    object Font : FirmwareType(
        label = "Font",
        extension = ".ft"
    )
    object Gps : FirmwareType(
        label = "Gps",
        extension = ".bin"
    )
}