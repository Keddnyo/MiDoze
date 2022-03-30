package io.github.keddnyo.midoze.utils

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.core.app.ActivityCompat
import io.github.keddnyo.midoze.R
import okhttp3.Request
import java.net.URL

class DozeRequest {
    fun getFirmwareLatest(): String {
        return URL("https://schakal.ru/fw/latest.json").readText()
    }
    fun getApplicationValues(): String {
        return URL("https://schakal.ru/fw/dev_apps.json").readText()
    }
    fun getFirmwareLinks(
        productionSource: String,
        deviceSource: String,
        appVersion: String,
        appname: String
    ): Request {
        val requestHost = "api-mifit-ru.huami.com"
        // val requestHost = "api-mifit-us2.huami.com"
        // val requestHost = "api.amazfit.com"

        val uriBuilder: Uri.Builder = Uri.Builder()
        uriBuilder.scheme("https")
            .authority(requestHost)
            .appendPath("devices")
            .appendPath("ALL")
            .appendPath("hasNewVersion")
            .appendQueryParameter("productId", "0")
            .appendQueryParameter("vendorSource", "0")
            .appendQueryParameter("resourceVersion", "0")
            .appendQueryParameter("firmwareFlag", "0")
            .appendQueryParameter("vendorId", "0")
            .appendQueryParameter("resourceFlag", "0")
            .appendQueryParameter("productionSource", productionSource)
            .appendQueryParameter("userid", "0")
            .appendQueryParameter("userId", "0")
            .appendQueryParameter("deviceSource", deviceSource)
            .appendQueryParameter("fontVersion", "0")
            .appendQueryParameter("fontFlag", "0")
            .appendQueryParameter("appVersion", appVersion)
            .appendQueryParameter("appid", "0")
            .appendQueryParameter("callid", "0")
            .appendQueryParameter("channel", "0")
            .appendQueryParameter("country", "0")
            .appendQueryParameter("cv", "0")
            .appendQueryParameter("device", "0")
            .appendQueryParameter("deviceType", "ALL")
            .appendQueryParameter("device_type", "android_phone")
            .appendQueryParameter("firmwareVersion", "0")
            .appendQueryParameter("hardwareVersion", "0")
            .appendQueryParameter("lang", "0")
            .appendQueryParameter("support8Bytes", "true")
            .appendQueryParameter("timezone", "0")
            .appendQueryParameter("v", "0")
            .appendQueryParameter("gpsVersion", "0")
            .appendQueryParameter("baseResourceVersion", "0")

        return Request.Builder()
            .url(uriBuilder.toString())
            .addHeader("hm-privacy-diagnostics", "false")
            .addHeader("country", "0")
            .addHeader("appplatform", "android_phone")
            .addHeader("hm-privacy-ceip", "0")
            .addHeader("x-request-id", "0")
            .addHeader("timezone", "0")
            .addHeader("channel", "0")
            .addHeader("user-agent", "0")
            .addHeader("cv", "0")
            .addHeader("appname", appname)
            .addHeader("v", "0")
            .addHeader("apptoken", "0")
            .addHeader("lang", "0")
            .addHeader("Host", requestHost)
            .addHeader("Connection", "Keep-Alive")
            .addHeader("accept-encoding", "gzip")
            .addHeader("accept", "*/*")
            .build()
    }
    fun getFirmwareFile(
        context: Context,
        fileUrl: String,
        deviceSource: String
    ) {
        val permissionCheck = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val fileName = URLUtil.guessFileName(fileUrl, "?", "?")
            val request = DownloadManager.Request(Uri.parse(fileUrl))

            request.setTitle(fileName)
            request.setNotificationVisibility(1)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${context.getString(R.string.app_name)}/$deviceSource/$fileName"
            )

            downloadManager.enqueue(request)
        }
    }
}