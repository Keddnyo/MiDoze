package io.github.keddnyo.midoze.ui.routes.apps

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.data_models.application.Application

object AppsContainer {
    val wearableApps: ArrayList<Application> = arrayListOf(
        Application(
            icon = R.drawable.ic_zepp,
            name = "Zepp",
        ),
        Application(
            icon = R.drawable.ic_zepp_life,
            name = "Zepp Life",
        ),
        Application(
            icon = R.drawable.ic_notify_amazfit,
            name = "Notify for Amazfit",
        ),
        Application(
            icon = R.drawable.ic_notify_mi_band,
            name = "Notify for Mi Band",
        ),
        Application(
            icon = R.drawable.ic_notify_mi_band_lite,
            name = "Notify for Mi Band Lite",
        ),
        Application(
            icon = R.drawable.ic_notify_lite,
            name = "Notify Lite",
        ),
        Application(
            icon = R.drawable.ic_mi_bandage,
            name = "Mi Bandage",
        ),
        Application(
            icon = R.drawable.ic_gadgetbridge,
            name = "Gadgetbridge",
        ),
        Application(
            icon = R.drawable.ic_huafetcher,
            name = "Huafetcher",
        ),
        Application(
            icon = R.drawable.ic_master_for_amazfit,
            name = "Master for Amazfit",
        ),
        Application(
            icon = R.drawable.ic_master_for_mi_band,
            name = "Master for Mi Band",
        ),
        Application(
            icon = R.drawable.ic_watch_droid_assistant,
            name = "Watch Droid Assistant",
        ),
        Application(
            icon = R.drawable.ic_tools_and_amazfit,
            name = "Tools & Amazfit",
        ),
        Application(
            icon = R.drawable.ic_tools_and_mi_band,
            name = "Tools & Mi Band",
        ),
    )
}