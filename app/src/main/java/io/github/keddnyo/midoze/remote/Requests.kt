package io.github.keddnyo.midoze.remote

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Device
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.local.devices.WearableRepository
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.Permissions
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL

class Requests {
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

    suspend fun getWatchfaceData(): String {
        val client = HttpClient {
            install(HttpCookies) {
                storage = ConstantCookiesStorage(Cookie(name = "locale", value = "en_us"))
            }
        }
        val targetHost = "watch-appstore.iot.mi.com"
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = targetHost
                appendPathSegments("api", "watchface", "prize", "tabs")
                parameter("model", "hqbd3.watch.l67")
            }
            headers {
                append("Accept-Language", "en-US,en")
                append("Content-Language", "en")
                append("Watch-Appstore-Common", "_locale=US&_language=en&_devtype=1&_ver=3.6.0i")
            }
        }.bodyAsText()
    }

    suspend fun getFirmwareData(
        context: Context,
        wearable: Wearable
    ): FirmwareData? = with(context as Activity) {
        val client = HttpClient()
        val targetHost = OnlineStatus(context).getXiaomiHostReachable().toString()
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = targetHost
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
                append("Host", targetHost)
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
        Permissions(context).run {
            fun downloadFile() {
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

            if (isWriteExternalStoragePermissionAvailable()) {
                downloadFile()
            } else {
                context.getString(R.string.permission_not_granted).showAsToast(context)
                requestWriteExternalStoragePermission()
            }
        }
    }

    fun getAppReleaseData(): JSONObject {
        return runBlocking(Dispatchers.IO) {
            JSONObject(URL(Routes.GITHUB_RELEASE_DATA_PAGE).readText())
        }
    }
}