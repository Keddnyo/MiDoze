package io.github.keddnyo.midoze.local.repository

import io.github.keddnyo.midoze.R

open class WearableApplication(
    val icon: Int,
    val name: String,
) {
    object Zepp : WearableApplication(
        icon = R.drawable.ic_zepp,
        name = "com.huami.midong"
    )

    object ZeppLife : WearableApplication(
        icon = R.drawable.ic_zepp_life,
        name = "com.xiaomi.hm.health",
    )
}