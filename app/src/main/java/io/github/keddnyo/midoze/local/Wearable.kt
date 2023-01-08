package io.github.keddnyo.midoze.local

data class Wearable(
    val deviceName: String,
    val devicePreview: Int,
    val deviceSource: Int,
    val productionSource: Int,
    val application: Application
)

open class Application (
    val appName: String,
    val appVersion: String,
) {
    object Zepp : Application(
        appName = "com.huami.midong",
        appVersion = "7.4.1-play_100950"
    )

    object ZeppLife : Application(
        appName = "com.xiaomi.hm.health",
        appVersion = "6.5.5_50677"
    )
}