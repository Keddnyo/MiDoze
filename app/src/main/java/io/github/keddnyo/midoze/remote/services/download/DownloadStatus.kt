package io.github.keddnyo.midoze.remote.services.download

interface DownloadStatus {
    object Successfully : DownloadStatus
    object Failed : DownloadStatus
}