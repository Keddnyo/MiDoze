package io.github.keddnyo.midoze.local.repositories

open class AppName(
    val appName: String,
) {
    object Zepp : AppName(
        appName = "com.huami.midong"
    )

    object ZeppLife : AppName(
        appName = "com.xiaomi.hm.health",
    )
}