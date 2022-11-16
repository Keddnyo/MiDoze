package io.github.keddnyo.midoze.remote.models.firmware

import android.net.Uri

data class FirmwareDownload(
    val fileType: FirmwareType,
    val fileVersion: String,
    val url: String
) {
    val firmwareUrl: Uri
        get() = Uri.parse(url)

    val fileName = StringBuilder()
        .append(fileType.label)
        .append("_")
        .append(fileVersion)
        .append(fileType.extension)
        .toString()
}