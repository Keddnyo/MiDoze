package io.github.keddnyo.midoze.local.repositories

import io.github.keddnyo.midoze.R

open class AppName(
    val appIcon: Int,
    val appName: String,
) {
    object Zepp : AppName(
        appIcon = R.drawable.ic_zepp,
        appName = "com.huami.midong"
    )

    object ZeppLife : AppName(
        appIcon = R.drawable.ic_zepp_life,
        appName = "com.xiaomi.hm.health",
    )
}