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
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.local.dataModels.Request
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.local.dataModels.Wearable
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
        application: Application
    ): ArrayList<FirmwareData> = with(context as Activity) {
        val deviceArrayList: ArrayList<FirmwareData> = arrayListOf()

        var firmwareData: JSONObject
        var devicePreview: Int
        var deviceName: String

        runBlocking {
            val prefs: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context)
            val isAdvancedSearch = prefs.getBoolean("filters_deep_search", false)

            val productionSourceLimit = if (isAdvancedSearch) {
                270
            } else {
                257
            }

            val deviceSourceLimit = if (isAdvancedSearch) {
                350
            } else {
                95
            }

            for (productionSource in 256..productionSourceLimit) {
                for (deviceSource in 0..deviceSourceLimit) {
                    firmwareData = DozeRequest().getFirmwareData(
                        productionSource.toString(),
                        deviceSource.toString(),
                        application.version,
                        application.name,
                        context
                    )

                    if (firmwareData.has("firmwareVersion")) {
                        val deviceData = DeviceRepository().getDeviceNameByCode(deviceSource, productionSource)

                        deviceName =
                            deviceData.name

                        devicePreview =
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

                        val host = prefs.getString("filters_request_host", "1").toString()
                        val region = prefs.getString("filters_request_region", "1").toString()
                        val zeppVersion = prefs.getString("filters_zepp_app_version", getString(R.string.filters_request_zepp_app_version_value)).toString()
                        val zeppLifeVersion = prefs.getString("filters_zepp_life_app_version", getString(R.string.filters_request_zepp_life_app_version_value)).toString()

                        deviceArrayList.add(
                            FirmwareData(
                                wearable = Wearable(deviceName, devicePreview),
                                application = application,
                                firmware = firmwareData,
                                firmwareVersion = firmwareData.getString("firmwareVersion"),
                                buildTime = StringUtils().getLocalFirmwareDate(get("buildTime")),
                                changeLog = get("changeLog"),
                                deviceSource = get("deviceSource"),
                                productionSource = get("productionSource"),
                                request = Request(host = host, region = region, isAdvancedSearch = isAdvancedSearch, zeppVersion =  zeppVersion, zeppLifeVersion = zeppLifeVersion)
                            )
                        )
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