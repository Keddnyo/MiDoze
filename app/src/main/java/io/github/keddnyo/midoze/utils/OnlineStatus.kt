package io.github.keddnyo.midoze.utils

import android.content.Context
import io.github.keddnyo.midoze.remote.Routes
import java.net.URL

class OnlineStatus(val context: Context) {
    fun isOnline(): Boolean =
        URL(Routes.GITHUB_REPOSITORY).isActive()

    fun URL.isActive() = openConnection().run {
        try {
            connectTimeout = 10000
            connect()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getXiaomiHostReachable(): String? {
        arrayListOf(
            Routes.API_AMAZFIT, Routes.API_MIFIT_RU, Routes.API_MIFIT_US2
        ).let { route ->
            for (i in route) {
                if (URL(i).isActive()) {
                    return i
                }
            }
            return null
        }
    }
}