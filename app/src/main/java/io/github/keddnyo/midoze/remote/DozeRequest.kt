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
import io.github.keddnyo.midoze.local.Region.REGION_ARRAY
import io.github.keddnyo.midoze.local.dataModels.*
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.remote.Routes.MIDOZE_HOST_FIRST
import io.github.keddnyo.midoze.remote.Routes.MIDOZE_HOST_SECOND
import io.github.keddnyo.midoze.remote.Routes.MIDOZE_HOST_THIRD
import io.github.keddnyo.midoze.utils.DozeLocale
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.*

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

    private fun isHostAvailable(host: String): Boolean {
        val connection: HttpURLConnection =
            URL("https://$host").openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        val responseCode = runBlocking(Dispatchers.IO) {
            connection.responseCode
        }
        return responseCode == 200
    }

    fun getHostReachable(): String? {
        return if (isHostAvailable(MIDOZE_HOST_FIRST)) {
            MIDOZE_HOST_FIRST
        } else if (isHostAvailable(MIDOZE_HOST_SECOND)) {
            MIDOZE_HOST_SECOND
        } else if (isHostAvailable(MIDOZE_HOST_THIRD)) {
            MIDOZE_HOST_THIRD
        } else {
            null
        }
    }

    fun getFirmwareLatest(
        context: Context,
        application: Application
    ): ArrayList<FirmwareData> = with(context as Activity) {
        val deviceArrayList: ArrayList<FirmwareData> = arrayListOf()

        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val isAdvancedSearch = prefs.getBoolean("filters_deep_scan", false)

        val productionSourceLimit = if (isAdvancedSearch) {
            265
        } else {
            257
        }

        val deviceSourceLimit = if (isAdvancedSearch) {
            345
        } else {
            95
        }

        for (productionSource in 256..productionSourceLimit) {
            for (deviceSource in 12..deviceSourceLimit) {
                fun getFirmwareRegionData(region: Region): FirmwareData? {
                    return runBlocking(Dispatchers.IO) {
                        DozeRequest().getFirmwareData(
                            context = context,
                            deviceSource = deviceSource.toString(),
                            productionSource = productionSource.toString(),
                            application = application,
                            region = region
                        )
                    }
                }

                (getFirmwareRegionData(REGION_ARRAY[0])
                ?: getFirmwareRegionData(REGION_ARRAY[1]))?.let {
                    deviceArrayList.add(
                        it
                    )
                }
            }
        }

        return deviceArrayList
    }

    suspend fun getFirmwareData(
        context: Context,
        deviceSource: String,
        productionSource: String,
        application: Application,
        region: Region
    ): FirmwareData? = with(context as Activity) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        val client = HttpClient()
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = getHostReachable().toString()
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
                parameter("appVersion", application.version)
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
                append("country", region.country)
                append("appplatform", "android_phone")
                append("hm-privacy-ceip", "0")
                append("x-request-id", "0")
                append("timezone", "0")
                append("channel", "0")
                append("user-agent", "0")
                append("cv", "0")
                append("appname", application.name)
                append("v", "0")
                append("apptoken", "0")
                append("lang", region.language)
                append("Host", getHostReachable().toString())
                append("Connection", "Keep-Alive")
                append("accept-encoding", "gzip")
                append("accept", "*/*")
            }
        }

        val firmwareData = JSONObject(response.bodyAsText())

        return if (firmwareData.has("firmwareVersion")) {
            val deviceData =
                DeviceRepository().getDeviceNameByCode(
                    deviceSource.toInt(),
                    productionSource.toInt()
                )

            val deviceName =
                deviceData.name

            val devicePreview = deviceData.image

            fun get(key: String): String {
                val f = firmwareData

                return if (f.has(key)) {
                    f.getString(key)
                } else ({
                    null
                }).toString()
            }

            val isAdvancedSearch = prefs.getBoolean("filters_deep_scan", false)

            FirmwareData(
                wearable = Wearable(deviceName, devicePreview),
                application = application,
                firmware = firmwareData,
                firmwareVersion = firmwareData.getString("firmwareVersion"),
                buildTime = get("buildTime"),
                changeLog = get("changeLog"),
                deviceSource = get("deviceSource"),
                productionSource = get("productionSource"),
                request = Request(
                    isAdvancedSearch = isAdvancedSearch,
                ),
                region = region
            )
        } else {
            null
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

    fun getAppReleaseData(): JSONObject {
        return JSONObject(URL(Routes.GITHUB_RELEASE_DATA_PAGE).readText())
    }
}