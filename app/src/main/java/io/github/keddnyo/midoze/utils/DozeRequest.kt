package io.github.keddnyo.midoze.utils

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URL

class DozeRequest {
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.allNetworkInfo
        for (i in info.indices) {
            if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

    fun getFirmwareLatest(): JSONObject {
        return JSONObject(URL("https://schakal.ru/fw/latest.json").readText())
    }

    fun getApplicationValues(): String {
        return URL("https://schakal.ru/fw/dev_apps.json").readText()
    }

    private fun getApplicationLatestReleaseInfo(context: Context): JSONObject {
        val appName = context.getString(R.string.app_name)
        return JSONObject(URL("https://api.github.com/repos/keddnyo/$appName/releases/latest").readText())
    }

    suspend fun getFirmwareLinks(
        productionSource: String,
        deviceSource: String,
        appVersion: String,
        appName: String,
        context: Context,
    ): JSONObject {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        val country = when (prefs.getString("settings_request_region", "1")) {
            "2" -> {
                "CH"
            }
            "3" -> {
                "RU"
            }
            "4" -> {
                "AR"
            }
            else -> {
                "US"
            }
        }
        val lang = when (prefs.getString("settings_request_region", "1")) {
            "2" -> {
                "zh_CH"
            }
            "3" -> {
                "ru_RU"
            }
            "4" -> {
                "ar_AR"
            }
            else -> {
                "en_US"
            }
        }

        val requestHost = when (prefs.getString("settings_request_host", "1")) {
            "2" -> {
                context.getString(R.string.request_host_second)
            }
            "3" -> {
                context.getString(R.string.request_host_third)
            }
            else -> {
                context.getString(R.string.request_host_first)
            }
        }

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

        val request = Request.Builder()
            .url(uriBuilder.toString())
            .addHeader("hm-privacy-diagnostics", "false")
            .addHeader("country", country)
            .addHeader("appplatform", "android_phone")
            .addHeader("hm-privacy-ceip", "0")
            .addHeader("x-request-id", "0")
            .addHeader("timezone", "0")
            .addHeader("channel", "0")
            .addHeader("user-agent", "0")
            .addHeader("cv", "0")
            .addHeader("appname", appName)
            .addHeader("v", "0")
            .addHeader("apptoken", "0")
            .addHeader("lang", lang)
            .addHeader("Host", requestHost)
            .addHeader("Connection", "Keep-Alive")
            .addHeader("accept-encoding", "gzip")
            .addHeader("accept", "*/*")
            .build()

        return withContext(Dispatchers.IO) {
            JSONObject(
                OkHttpClient().newCall(request).execute().body()?.string().toString()
            )
        }
    }

    fun getFirmwareFile(
        context: Context,
        fileUrl: String,
        subName: String,
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
                "${context.getString(R.string.app_name)}/$subName/$fileName"
            )

            downloadManager.enqueue(request)
        }
    }

    fun updateChecker(context: Context, prefs: SharedPreferences) = with(context) {
        if (prefs.getBoolean("settings_app_update_checker", true)) {
            val latestReleaseInfoJson = runBlocking {
                withContext(Dispatchers.IO) {
                    DozeRequest().getApplicationLatestReleaseInfo(context)
                }
            }

            if (latestReleaseInfoJson.has("tag_name")) {
                val latestVersion = latestReleaseInfoJson.getString("tag_name")
                val latestVersionLink =
                    latestReleaseInfoJson.getJSONArray("assets").getJSONObject(0)
                        .getString("browser_download_url")

                if (BuildConfig.VERSION_NAME < latestVersion) {
                    val builder = AlertDialog.Builder(context)
                        .setTitle(getString(R.string.update_dialog_title))
                        .setMessage(getString(R.string.update_dialog_message))
                        .setIcon(R.drawable.ic_info)
                        .setCancelable(false)
                    builder.setPositiveButton(R.string.update_dialog_button) { _: DialogInterface?, _: Int ->
                        DozeRequest().getFirmwareFile(context,
                            latestVersionLink,
                            getString(R.string.app_name))
                        UiUtils().showToast(context, getString(R.string.downloading_toast))
                        DialogInterface.BUTTON_POSITIVE
                    }
                    builder.setNegativeButton(android.R.string.cancel) { _: DialogInterface?, _: Int ->
                        DialogInterface.BUTTON_NEGATIVE
                    }
                    builder.show()
                }
            }
        }
    }
}