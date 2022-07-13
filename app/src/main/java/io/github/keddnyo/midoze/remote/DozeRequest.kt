package io.github.keddnyo.midoze.remote

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.devices.FirmwareData
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.utils.StringUtils
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
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

    fun getFirmwareLatest(
        context: Context,
        appName: String,
        appVersion: String
    ): ArrayList<FirmwareData> = with(context as Activity) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val deviceArrayList: ArrayList<FirmwareData> = arrayListOf()

        var firmwareData: JSONObject
        var deviceIcon: Int
        var deviceName: String

        val productionSourceRange = prefs.getInt("filters_feed_productionSource_range", 257)
        val deviceSourceRange = prefs.getInt("filters_feed_deviceSource_range", 180)

        runBlocking {
            for (productionSource in 256..productionSourceRange) {
                for (deviceSource in 0..deviceSourceRange) {
                    firmwareData = DozeRequest().getFirmwareData(
                        productionSource.toString(),
                        deviceSource.toString(),
                        appVersion,
                        appName,
                        context
                    )

                    if (firmwareData.has("firmwareVersion")) {
                        val deviceData = DeviceRepository().getDeviceNameByCode(deviceSource, productionSource)

                        deviceName =
                            deviceData.name

                        deviceIcon =
                            deviceData.image

                        /*deviceIcon = if (deviceName.contains("Xiaomi")) {
                            R.drawable.ic_xiaomi
                        } else if (deviceName.contains("Zepp")) {
                            R.drawable.ic_zepp
                        } else {
                            R.drawable.ic_amazfit
                        }*/

                        fun get(key: String): String {
                            val f = firmwareData

                            return if (f.has(key)) {
                                f.getString(key)
                            } else ({
                                null
                            }).toString()
                        }

                        if (deviceName.contains("Unknown") == prefs.getBoolean(
                                "filters_show_unknown_devices",
                                false
                            )
                        ) {
                            deviceArrayList.add(
                                FirmwareData(
                                    icon = deviceIcon,
                                    name = deviceName,
                                    firmware = firmwareData,
                                    firmwareVersion = firmwareData.getString("firmwareVersion"),
                                    buildTime = StringUtils().getLocalFirmwareDate(get("buildTime")),
                                    changeLog = get("changeLog"),
                                    deviceSource = get("deviceSource"),
                                    productionSource = get("productionSource"),
                                    appName = appName,
                                    appVersion = appVersion
                                )
                            )
                        }
                    }
                }
            }
        }

        return deviceArrayList
    }

    fun getAppReleaseData(): JSONObject {
        return JSONObject(URL(Routes.GITHUB_RELEASE_DATA_PAGE).readText())
    }

    suspend fun getFirmwareData(
        productionSource: String,
        deviceSource: String,
        appVersion: String,
        appName: String,
        context: Context
    ): JSONObject = with(context as Activity) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        val requestHost = when (prefs.getString("filters_request_host", "1")) {
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

        val country = when (prefs.getString("filters_request_region", "1")) {
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
        val lang = when (prefs.getString("filters_request_region", "1")) {
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

        val client = HttpClient()
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = requestHost
                appendPathSegments("devices", "ALL", "hasNewVersion")
                parameter("productId", "0")
                parameter("vendorSource", "0")
                parameter("resourceVersion", "0")
                parameter("firmwareFlag", "0")
                parameter("vendorId", "0")
                parameter("resourceFlag", "0")
                parameter("productionSource", productionSource)
                parameter("userid", "0")
                parameter("userId", "0")
                parameter("deviceSource", deviceSource)
                parameter("fontVersion", "0")
                parameter("fontFlag", "0")
                parameter("appVersion", appVersion)
                parameter("appid", "0")
                parameter("callid", "0")
                parameter("channel", "0")
                parameter("country", "0")
                parameter("cv", "0")
                parameter("device", "0")
                parameter("deviceType", "ALL")
                parameter("device_type", "android_phone")
                parameter("firmwareVersion", "0")
                parameter("hardwareVersion", "0")
                parameter("lang", "0")
                parameter("support8Bytes", "true")
                parameter("timezone", "0")
                parameter("v", "0")
                parameter("gpsVersion", "0")
                parameter("baseResourceVersion", "0")
            }
            headers {
                append("hm-privacy-diagnostics", "false")
                append("country", country)
                append("appplatform", "android_phone")
                append("hm-privacy-ceip", "0")
                append("x-request-id", "0")
                append("timezone", "0")
                append("channel", "0")
                append("user-agent", "0")
                append("cv", "0")
                append("appname", appName)
                append("v", "0")
                append("apptoken", "0")
                append("lang", lang)
                append("Host", requestHost)
                append("Connection", "Keep-Alive")
                append("accept-encoding", "gzip")
                append("accept", "*/*")
            }
        }
        return JSONObject(response.bodyAsText())
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
}