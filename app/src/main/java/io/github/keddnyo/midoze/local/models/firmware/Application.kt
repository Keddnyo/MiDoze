package io.github.keddnyo.midoze.local.models.firmware

import io.github.keddnyo.midoze.local.repository.AppName
import io.github.keddnyo.midoze.local.repository.AppVersion

open class Application (
    val instance: AppName,
    val appVersion: AppVersion,
) {
    object Zepp : Application(
        instance = AppName.Zepp,
        appVersion = AppVersion.Zepp,
    )

    object ZeppLife : Application(
        instance = AppName.ZeppLife,
        appVersion = AppVersion.ZeppLife,
    )
}