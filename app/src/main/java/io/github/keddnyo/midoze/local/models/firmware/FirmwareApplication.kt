package io.github.keddnyo.midoze.local.models.firmware

open class FirmwareApplication (
    val appName: String,
    val appVersion: String,
) {
    object Zepp : FirmwareApplication(
        appName = "com.huami.midong",
        appVersion = "7.2.0-play_100865"
    )

    object ZeppLife : FirmwareApplication(
        appName = "com.xiaomi.hm.health",
        appVersion = "6.3.5_50638"
    )
}