package io.github.keddnyo.midoze.remote

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.core.app.ActivityCompat
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Device
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.local.devices.WearableRepository
import io.github.keddnyo.midoze.remote.Routes.XIAOMI_HOST_FIRST
import io.github.keddnyo.midoze.remote.Routes.XIAOMI_HOST_SECOND
import io.github.keddnyo.midoze.remote.Routes.XIAOMI_HOST_THIRD
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Requests {
    fun isOnline(context: Context): Boolean {
        return runBlocking(Dispatchers.Default) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.allNetworkInfo
            for (i in info.indices) {
                if (info[i].state == NetworkInfo.State.CONNECTED) {
                    return@runBlocking true
                }
            }
            false
        }
    }

    fun isHostAvailable(host: String): Boolean {
        val connection: HttpURLConnection =
            URL(host).openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        val responseCode = runBlocking(Dispatchers.IO) {
            connection.responseCode
        }
        return responseCode == 200
    }

    private fun getXiaomiHostReachable(): String? {
        return if (isHostAvailable("https://$XIAOMI_HOST_FIRST")) {
            XIAOMI_HOST_FIRST
        } else if (isHostAvailable("https://$XIAOMI_HOST_SECOND")) {
            XIAOMI_HOST_SECOND
        } else if (isHostAvailable("https://$XIAOMI_HOST_THIRD")) {
            XIAOMI_HOST_THIRD
        } else {
            null
        }
    }

    fun getFirmwareLatest(
        context: Context
    ): ArrayList<FirmwareDataStack> = with(context as Activity) {
        val deviceArrayListArray: ArrayList<FirmwareDataStack> = arrayListOf()

        WearableRepository(context).wearables.forEach { wearableStack ->
            val deviceArrayList: ArrayList<FirmwareData> = arrayListOf()
            wearableStack.wearableStack.forEach { wearable ->
                runBlocking(Dispatchers.IO) {
                    Requests().getFirmwareData(
                        context = context,
                        wearable = wearable
                    )
                }?.let {
                    deviceArrayList.add(
                        it
                    )
                }
            }
            deviceArrayListArray.add(
                FirmwareDataStack(
                    name = wearableStack.name,
                    deviceStack = deviceArrayList
                )
            )
        }

        return deviceArrayListArray
    }

    suspend fun getFirmwareData(
        context: Context,
        wearable: Wearable
    ): FirmwareData? = with(context as Activity) {
        val client = HttpClient()
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = getXiaomiHostReachable().toString()
                appendPathSegments("devices", "ALL", "hasNewVersion")
                parameter("productId", "0")
                parameter("vendorSource", "0")
                parameter("resourceVersion", "0")
                parameter("firmwareFlag", "0")
                parameter("vendorId", "0")
                parameter("resourceFlag", "0")
                parameter("productionSource", wearable.productionSource)
                parameter("userid", "0")
                parameter("userId", "0")
                parameter("deviceSource", wearable.deviceSource)
                parameter("fontVersion", "0")
                parameter("fontFlag", "0")
                parameter("appVersion", wearable.application.version)
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
                append("country", wearable.region.country)
                append("appplatform", "android_phone")
                append("hm-privacy-ceip", "0")
                append("x-request-id", "0")
                append("timezone", "0")
                append("channel", "0")
                append("user-agent", "0")
                append("cv", "0")
                append("appname", wearable.application.name)
                append("v", "0")
                append("apptoken", "0")
                append("lang", wearable.region.language)
                append("Host", getXiaomiHostReachable().toString())
                append("Connection", "Keep-Alive")
                append("accept-encoding", "gzip")
                append("accept", "*/*")
            }
        }

        val firmwareData = JSONObject(response.bodyAsText())

        return if (firmwareData.has("firmwareVersion")) {
            val deviceData =
                DeviceRepository().getDeviceNameByCode(
                    wearable.deviceSource.toInt(),
                    wearable.productionSource.toInt()
                )

            val deviceName =
                deviceData.name

            val devicePreview = deviceData.image

            fun get(key: String): String {
                return if (firmwareData.has(key)) {
                    firmwareData.getString(key)
                } else ({
                    null
                }).toString()
            }

            FirmwareData(
                device = Device(deviceName, devicePreview),
                wearable = wearable,
                firmware = firmwareData,
                firmwareVersion = firmwareData.getString("firmwareVersion"),
                changeLog = get("changeLog")
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
            if (android.os.Build.VERSION.SDK_INT >= 21) {
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
            } else {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl))
                )
            }
        }
    }

    fun getAppReleaseData(): JSONObject {
        return runBlocking(Dispatchers.IO) {
            JSONObject(URL(Routes.GITHUB_RELEASE_DATA_PAGE).readText())
        }
    }
}