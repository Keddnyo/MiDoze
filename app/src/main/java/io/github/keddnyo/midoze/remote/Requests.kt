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
import io.github.keddnyo.midoze.local.dataModels.Firmware
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.local.devices.WearableRepository
import io.github.keddnyo.midoze.utils.DozeLocale
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.Permissions
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import io.ktor.client.*
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
            val deviceArrayList: ArrayList<Firmware> = arrayListOf()
            wearableStack.wearableStack.forEach { wearable ->
                runBlocking(Dispatchers.IO) {
                    Requests().getFirmwareData(
                        context = context,
                        wearable = wearable
                    )
                }?.let { device ->
                    deviceArrayList.add(
                        device
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

    suspend fun getWatchfaceData(deviceName: String): String {
        val client = HttpClient()
        val targetHost = "watch-appstore.iot.mi.com"
        val country = DozeLocale().currentCountry
        val language = DozeLocale().currentLanguage

        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = targetHost
                appendPathSegments("api", "watchface", "prize", "tabs")
                parameter("model", deviceName)
            }
            headers {
                append("Watch-Appstore-Common", "_locale=$country&_language=$language")
            }
        }.bodyAsText()
    }

    suspend fun getFirmwareData(
        context: Context,
        wearable: Wearable
    ): Firmware? = with(context as Activity) {
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
        }.bodyAsText()

        val firmwareData = JSONObject(response)

        return if (firmwareData.has("firmwareVersion")) {
            val device =
                DeviceRepository().getDeviceNameByCode(
                    wearable.deviceSource.toInt()
                )

            val deviceName = device.name
            val devicePreview = device.preview

            Firmware(
                device = Device(deviceName, devicePreview),
                wearable = wearable,
                firmwareData = firmwareData
            )
        } else {
            null
        }
    }

    fun getFirmwareFile(
        context: Context,
        fileUrl: String,
        subName: String,
        fileType: String
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
                        "${context.getString(R.string.app_name)}/$fileType/$subName/$fileName"
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
                (context as Activity).runOnUiThread {
                    context.getString(R.string.permission_not_granted).showAsToast(context)
                    requestWriteExternalStoragePermission()
                }
            }
        }
    }

    fun getAppReleaseData(): JSONObject {
        return runBlocking(Dispatchers.IO) {
            JSONObject(URL(Routes.GITHUB_RELEASE_DATA_PAGE).readText())
        }
    }
}