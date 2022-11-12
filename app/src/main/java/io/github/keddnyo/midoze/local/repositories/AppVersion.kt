package io.github.keddnyo.midoze.local.repositories

open class AppVersion(
    val appVersion: String
) {
    object Zepp : AppVersion(
        appVersion = "7.2.0-play_100865"
    )

    object ZeppLife : AppVersion(
        appVersion = "6.3.5_50638"
    )
}