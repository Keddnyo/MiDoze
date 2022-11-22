package io.github.keddnyo.midoze.remote.requests

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.FirmwareEntity

fun downloadFirmware(
    context: Context,
    deviceName: String,
    firmwareEntity: FirmwareEntity
) = with(context) {

    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val url = firmwareEntity.firmwareUri

    val downloadsDir = Environment.DIRECTORY_DOWNLOADS
    val appName = getString(R.string.app_name)
    val deviceDir = "$appName/$deviceName"
    val fileName = "$deviceDir/${firmwareEntity.fileName}"

    try {
        val request = DownloadManager.Request(url)
            .setTitle(
                "$deviceName — ${firmwareEntity.firmwareLabel.label} ${firmwareEntity.firmwareVersion}"
            )
            .setDestinationInExternalPublicDir(
                downloadsDir, fileName
            )
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )

        downloadManager.enqueue(request)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}