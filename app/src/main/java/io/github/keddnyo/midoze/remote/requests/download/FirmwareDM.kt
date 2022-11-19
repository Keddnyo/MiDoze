package io.github.keddnyo.midoze.remote.requests.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareEntity

fun downloadFirmware(
    context: Context,
    deviceName: String,
    firmwareEntity: FirmwareEntity
) = with(context) {

    /**
     * **Processing the input URL**
     */
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val url = firmwareEntity.firmwareUri

    /**
     * File will be downloaded in:
     *
     * '/storage/emulated/0/Downloads/MiDoze/Firmwares/DeviceName/FileName.extension'
     */
    val downloadsDir = Environment.DIRECTORY_DOWNLOADS
    val appName = getString(R.string.app_name)
    val firmwareTitle = getString(R.string.roms)
    val firmwareDir = "$appName/$firmwareTitle"
    val deviceDir = "$firmwareDir/$deviceName"
    val fileName = "$deviceDir/${firmwareEntity.fileName}"

    try {
        val request = DownloadManager.Request(url)
            .setAllowedOverRoaming(
                false
            )
            .setDestinationInExternalPublicDir(
                downloadsDir, fileName
            )
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setTitle(
                "$deviceName — ${firmwareEntity.firmwareLabel.label} ${firmwareEntity.firmwareVersion}"
            )


        downloadManager.enqueue(request)
        Toast.makeText(context, context.getString(R.string.downloadInProgress), Toast.LENGTH_SHORT)
            .show()
    } catch (e: Exception) {
        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT)
            .show()
    }
}