package io.github.keddnyo.midoze.local.toolbox

import android.content.Context
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Toolbox

class ToolboxRepository(val context: Context) {
    val items = arrayListOf(
        Toolbox(
            icon = R.drawable.ic_zepp,
            title = "Zepp",
            tag = "com.huami.watch.hmwatchmanager",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_mi_fit,
            title = "Zepp Life",
            tag = "com.xiaomi.hm.health",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_notify_lite_mi_band,
            title = "Notify Lite for Mi Band",
            tag = "com.mc.mibandlite1",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_notify_lite,
            title = "Notify Lite",
            tag = "com.mc.notify",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_notify_mi_band,
            title = "Notify for Mi Band",
            tag = "com.mc.amazfit1",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_notify_amazfit,
            title = "Notify for Amazfit",
            tag = "com.mc.miband1",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_mi_bandage,
            title = "Mi Bandage",
            tag = "hu.tiborsosdevs.mibandage",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_gadgetbridge,
            title = "Gadgetbridge",
            tag = "nodomain.freeyourgadget.gadgetbridge",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_huafetcher,
            title = "Huafetcher",
            tag = "nodomain.nopackage.huafetcher",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_watch_droid_assistant,
            title = "Watch Droid Assistant",
            tag = "com.lumaticsoft.watchdroidassistant",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_master_for_amazfit,
            title = "Master for Amazfit",
            tag = "blacknote.amazfitmaster",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_master_for_mi_band,
            title = "Master for Mi Band",
            tag = "blacknote.mibandmaster",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_tools_and_amazfit,
            title = "Tools & Amazfit",
            tag = "cz.zdenekhorak.amazfittools",
            isApp = true
        ),
        Toolbox(
            icon = R.drawable.ic_tools_and_mi_band,
            title = "Tools & Mi Band",
            tag = "cz.zdenekhorak.mibandtools",
            isApp = true
        ),

        Toolbox(
            icon = R.drawable.ic_toolbox,
            title = context.resources.getString(R.string.menu_explore_firmwares),
            tag = "Explore_firmwares",
            isApp = false
        ),
        Toolbox(
            icon = R.drawable.ic_request,
            title = context.resources.getString(R.string.menu_custom_request),
            tag = "Request",
            isApp = false
        ),
        Toolbox(
            icon = R.drawable.ic_download,
            title = context.resources.getString(R.string.app_downloads),
            tag = "Downloads",
            isApp = false
        ),
        Toolbox(
            icon = R.drawable.ic_delete,
            title = context.resources.getString(R.string.clear_cache),
            tag = "Clear_cache",
            isApp = false
        ),
        Toolbox(
            icon = R.drawable.ic_delete,
            title = context.resources.getString(R.string.app_uninstall),
            tag = "Uninstall",
            isApp = false
        )
    )
}