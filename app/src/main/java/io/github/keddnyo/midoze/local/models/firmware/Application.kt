package io.github.keddnyo.midoze.local.models.firmware

open class Application (
    val appName: String,
    val appVersion: String,
) {
    object Zepp : Application(
        appName = "com.huami.midong",
        appVersion = "7.2.0-play_100865"
    )

    object ZeppLife : Application(
        appName = "com.xiaomi.hm.health",
        appVersion = "6.3.5_50638"
    )
}