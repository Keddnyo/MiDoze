package io.github.keddnyo.midoze.remote.services.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareDownload
import java.io.File

fun download(
    context: Context,
    deviceName: String,
    firmwareDownload: FirmwareDownload
): DownloadStatus = with(context) {
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val url = Uri.parse(firmwareDownload.address)
    val fileName = File(url.path.toString()).name
    val request = DownloadManager.Request(url)

    request
        .setTitle("$deviceName — ${firmwareDownload.fileType.label} ${firmwareDownload.fileVersion}")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOCUMENTS,
            deviceName +
                    File.separator +
                    firmwareDownload.fileType.label +
                    "_" +
                    firmwareDownload.fileVersion +
                    firmwareDownload.fileType.extension
        )
        .setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
        )
        .setAllowedOverRoaming(false)

    try {
        val file = downloadManager.enqueue(request)
//        val filePath = downloadManager.getUriForDownloadedFile(file)

        return DownloadStatus.Successfully(
            fileName = fileName,
//            filePath = filePath
        )
    } catch (e: Exception) {
        return DownloadStatus.Failed(
            message = e.message.toString()
        )
    }
}