package io.github.keddnyo.midoze.remote.models.firmware

import android.net.Uri

data class FirmwareDownload(
    val fileType: FirmwareType,
    val fileVersion: String,
    val address: String
) {
    val firmwareUrl: Uri
        get() = Uri.parse(address)
}