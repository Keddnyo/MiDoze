package io.github.keddnyo.midoze.remote.requests.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.models.watchface.Watchface
import java.io.File

fun downloadWatchface(
    context: Context,
    watchface: Watchface
) = with(context) {

    /**
     * **Processing the input URL**
     */
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val url = watchface.watchfaceUrl

    val downloadsDir = Environment.DIRECTORY_DOWNLOADS
    val appName = getString(R.string.app_name)
    val appDir = File(downloadsDir, appName).toString()
    val watchfaceTitle = getString(R.string.watchfaces)
    val watchfaceDir = File(appDir, watchfaceTitle).toString()
    val fileName = watchface.fileName

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
            watchface.title
        )

    downloadManager.enqueue(request)
    Toast.makeText(context, context.getString(R.string.downloadInProgress), Toast.LENGTH_SHORT).show()

}