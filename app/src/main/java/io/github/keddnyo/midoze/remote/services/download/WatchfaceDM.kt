package io.github.keddnyo.midoze.remote.services.download

import android.app.DownloadManager
import android.content.Context

fun downloadWatchface(
    context: Context,
): DownloadStatus = with(context) {

    /**
     * **Processing the input URL**
     */
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    return try {

        DownloadStatus.Successfully
    } catch (_: Exception) {
        DownloadStatus.Failed
    }
}