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
        appVersion = "7.2.8-play_100895"
    )

    object ZeppLife : Application(
        appName = "com.xiaomi.hm.health",
        appVersion = "6.4.1_50651"
    )
}