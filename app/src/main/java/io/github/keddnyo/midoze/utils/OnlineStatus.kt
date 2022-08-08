package io.github.keddnyo.midoze.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.github.keddnyo.midoze.remote.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URL

class OnlineStatus(val context: Context) {
    val isOnline: Boolean = runBlocking(Dispatchers.Default) {
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

    fun isHostAvailable(host: String): Boolean {
        val connection: HttpURLConnection =
            URL(host).openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        val responseCode = runBlocking(Dispatchers.IO) {
            connection.responseCode
        }
        return responseCode == 200
    }

    fun getXiaomiHostReachable(): String? {
        return if (isHostAvailable("https://${Routes.XIAOMI_HOST_FIRST}")) {
            Routes.XIAOMI_HOST_FIRST
        } else if (isHostAvailable("https://${Routes.XIAOMI_HOST_SECOND}")) {
            Routes.XIAOMI_HOST_SECOND
        } else if (isHostAvailable("https://${Routes.XIAOMI_HOST_THIRD}")) {
            Routes.XIAOMI_HOST_THIRD
        } else {
            null
        }
    }
}