package io.github.keddnyo.midoze.remote

import android.net.Uri
import io.github.keddnyo.midoze.local.Wearable

data class Firmware(
    val wearable: Wearable,
    val firmwareEntities: ArrayList<FirmwareEntity>,
    val changeLog: String? = null
)

data class FirmwareEntity(
    val firmwareVersion: String,
    val firmwareUrl: String,
    val firmwareLabel: FirmwareLabel
) {
    val firmwareUri: Uri
        get() = Uri.parse(firmwareUrl)

    val fileName = StringBuilder()
        .append(firmwareLabel.label)
        .append("_")
        .append(firmwareVersion)
        .append(".")
        .append(
            firmwareUrl.substringAfterLast(".")
        )
        .toString()
}

open class FirmwareLabel(
    val label: String
) {
    object Firmware : FirmwareLabel("FW")
    object Resource : FirmwareLabel("RES")
    object BaseResource : FirmwareLabel("BaseRES")
    object Font : FirmwareLabel("FT")
    object Gps : FirmwareLabel("GPS")
}