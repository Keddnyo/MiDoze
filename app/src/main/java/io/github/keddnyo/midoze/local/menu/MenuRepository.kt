package io.github.keddnyo.midoze.local.menu

import android.content.Context
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.MainMenu

class MenuRepository(val context: Context) {
    val items = arrayListOf(
        MainMenu(
            icon = R.drawable.ic_firmware,
            title = context.resources.getString(R.string.menu_firmwares),
            tag = "Firmwares"
        ),
        MainMenu(
            icon = R.drawable.ic_watchface,
            title = context.resources.getString(R.string.menu_watchface),
            tag = "Watchface"
        ),
        MainMenu(
            icon = R.drawable.ic_apps,
            title = context.resources.getString(R.string.menu_apps),
            tag = "Apps"
        ),
        MainMenu(
            icon = R.drawable.ic_request,
            title = context.resources.getString(R.string.menu_request),
            tag = "Request"
        ),
        MainMenu(
            icon = R.drawable.ic_download,
            title = context.resources.getString(R.string.menu_downloads),
            tag = "Downloads"
        ),
        MainMenu(
            icon = R.drawable.ic_info,
            title = context.resources.getString(R.string.menu_github),
            tag = "About"
        ),
        MainMenu(
            icon = R.drawable.ic_delete,
            title = context.resources.getString(R.string.menu_uninstall),
            tag = "Uninstall"
        )
    )
}