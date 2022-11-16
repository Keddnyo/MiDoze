package io.github.keddnyo.midoze.remote.requests.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareDownload
import java.io.File

fun downloadFirmware(
    context: Context,
    deviceName: String,
    firmwareDownload: FirmwareDownload
) = with(context) {

    /**
     * **Processing the input URL**
     */
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val url = firmwareDownload.firmwareUrl

    /** **Declaring downloading file path.**
     *
     * File will be downloaded in:
     *
     * '/storage/emulated/0/Downloads/MiDoze/Firmwares/DeviceName/FileName.extension'
     *
     * Example:
     *
     * '/storage/emulated/0/Downloads/MiDoze/Firmwares/Amazfit Bip/Firmware_1.1.6.34.fw'
    */
    val downloadsDir = Environment.DIRECTORY_DOWNLOADS
    val appName = getString(R.string.app_name)
    val appDir = File(downloadsDir, appName).toString()
    val firmwareTitle = getString(R.string.firmwares)
    val firmwareDir = File(appDir, firmwareTitle).toString()
    val deviceDir = File(firmwareDir, deviceName).toString()
    val fileName = firmwareDownload.fileName

    /**
     * **Setting request parameters**
     */
    val request = DownloadManager.Request(url)
        .setAllowedOverRoaming(
            false
        )
        .setDestinationInExternalPublicDir(
            deviceDir, fileName
        )
        .setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        )
        .setTitle(
            "$deviceName — ${firmwareDownload.fileType.label} ${firmwareDownload.fileVersion}"
        )

    downloadManager.enqueue(request)
    Toast.makeText(context, context.getString(R.string.downloadInProgress), Toast.LENGTH_SHORT).show()

}