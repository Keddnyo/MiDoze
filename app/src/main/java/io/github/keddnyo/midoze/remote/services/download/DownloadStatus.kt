package io.github.keddnyo.midoze.remote.services.download

import android.net.Uri

sealed class DownloadStatus {
    data class Successfully(
        val fileName: String,
//        val filePath: Uri?
    ) : DownloadStatus()

    data class Failed(
        val message: String
    ) : DownloadStatus()
}