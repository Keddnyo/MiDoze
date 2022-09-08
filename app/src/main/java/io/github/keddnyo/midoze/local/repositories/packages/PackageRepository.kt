package io.github.keddnyo.midoze.local.repositories.packages

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.ApplicationData

class PackageRepository {
    val packages: ArrayList<ApplicationData.Application> = arrayListOf(
        ApplicationData.Application(
            icon = R.drawable.ic_zepp,
            title = "Zepp",
            tag = "com.huami.watch.hmwatchmanager"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_mi_fit,
            title = "Zepp Life",
            tag = "com.xiaomi.hm.health"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_notify_lite_mi_band,
            title = "Notify Lite for Mi Band",
            tag = "com.mc.mibandlite1"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_notify_lite,
            title = "Notify Lite",
            tag = "com.mc.notify"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_notify_mi_band,
            title = "Notify for Mi Band",
            tag = "com.mc.miband1"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_notify_amazfit,
            title = "Notify for Amazfit",
            tag = "com.mc.amazfit1"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_mi_bandage,
            title = "Mi Bandage",
            tag = "hu.tiborsosdevs.mibandage"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_gadgetbridge,
            title = "Gadgetbridge",
            tag = "nodomain.freeyourgadget.gadgetbridge"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_huafetcher,
            title = "Huafetcher",
            tag = "nodomain.nopackage.huafetcher"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_watch_droid_assistant,
            title = "Watch Droid Assistant",
            tag = "com.lumaticsoft.watchdroidassistant"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_master_for_amazfit,
            title = "Master for Amazfit",
            tag = "blacknote.amazfitmaster"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_master_for_mi_band,
            title = "Master for Mi Band",
            tag = "blacknote.mibandmaster"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_tools_and_amazfit,
            title = "Tools & Amazfit",
            tag = "cz.zdenekhorak.amazfittools"
        ),
        ApplicationData.Application(
            icon = R.drawable.ic_tools_and_mi_band,
            title = "Tools & Mi Band",
            tag = "cz.zdenekhorak.mibandtools"
        ),
    )
}