package io.github.keddnyo.midoze.local.repository

import io.github.keddnyo.midoze.R

open class WearableApplication(
    val appProductName: Int,
    val appProductIcon: Int,
    val appPackageName: String,
) {
    object Zepp : WearableApplication(
        appProductName = R.string.name_zepp,
        appProductIcon = R.drawable.ic_zepp,
        appPackageName = "com.huami.midong",
    )

    object ZeppLife : WearableApplication(
        appProductName = R.string.name_zepp_life,
        appProductIcon = R.drawable.ic_zepp_life,
        appPackageName = "com.xiaomi.hm.health",
    )
}