package io.github.keddnyo.midoze.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.github.keddnyo.midoze.remote.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.URL

class OnlineStatus {
    fun isOnline(context: Context): Boolean = runBlocking(Dispatchers.Default) {
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

    fun URL.isActive() = try {
        openConnection().run {
            connectTimeout = 50000
            connect()
            true
        }
    } catch (e: Exception) {
        false
    }

    fun getXiaomiHostReachable(): String? {
        arrayListOf(
            Routes.API_AMAZFIT, Routes.API_MIFIT_RU, Routes.API_MIFIT_US2
        ).let { route ->
            for (i in route) {
                if (URL("https://$i").isActive()) {
                    return i
                }
            }
            return null
        }
    }
}