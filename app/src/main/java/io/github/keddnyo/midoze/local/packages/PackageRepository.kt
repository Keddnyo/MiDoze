package io.github.keddnyo.midoze.local.packages

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.MainMenu

class PackageRepository {
    val packages: ArrayList<MainMenu> = arrayListOf(
        MainMenu(
            icon = R.drawable.ic_zepp,
            title = "Zepp",
            tag = "com.huami.watch.hmwatchmanager"
        ),
        MainMenu(
            icon = R.drawable.ic_mi_fit,
            title = "Zepp Life",
            tag = "com.xiaomi.hm.health"
        ),
        MainMenu(
            icon = R.drawable.ic_notify_lite_mi_band,
            title = "Notify Lite for Mi Band",
            tag = "com.mc.mibandlite1"
        ),
        MainMenu(
            icon = R.drawable.ic_notify_lite,
            title = "Notify Lite",
            tag = "com.mc.notify"
        ),
        MainMenu(
            icon = R.drawable.ic_notify_mi_band,
            title = "Notify for Mi Band",
            tag = "com.mc.miband1"
        ),
        MainMenu(
            icon = R.drawable.ic_notify_amazfit,
            title = "Notify for Amazfit",
            tag = "com.mc.amazfit1"
        ),
        MainMenu(
            icon = R.drawable.ic_mi_bandage,
            title = "Mi Bandage",
            tag = "hu.tiborsosdevs.mibandage"
        ),
        MainMenu(
            icon = R.drawable.ic_gadgetbridge,
            title = "Gadgetbridge",
            tag = "nodomain.freeyourgadget.gadgetbridge"
        ),
        MainMenu(
            icon = R.drawable.ic_huafetcher,
            title = "Huafetcher",
            tag = "nodomain.nopackage.huafetcher"
        ),
        MainMenu(
            icon = R.drawable.ic_watch_droid_assistant,
            title = "Watch Droid Assistant",
            tag = "com.lumaticsoft.watchdroidassistant"
        ),
        MainMenu(
            icon = R.drawable.ic_master_for_amazfit,
            title = "Master for Amazfit",
            tag = "blacknote.amazfitmaster"
        ),
        MainMenu(
            icon = R.drawable.ic_master_for_mi_band,
            title = "Master for Mi Band",
            tag = "blacknote.mibandmaster"
        ),
        MainMenu(
            icon = R.drawable.ic_tools_and_amazfit,
            title = "Tools & Amazfit",
            tag = "cz.zdenekhorak.amazfittools"
        ),
        MainMenu(
            icon = R.drawable.ic_tools_and_mi_band,
            title = "Tools & Mi Band",
            tag = "cz.zdenekhorak.mibandtools"
        ),
    )
}