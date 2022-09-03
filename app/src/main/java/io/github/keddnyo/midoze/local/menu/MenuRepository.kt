package io.github.keddnyo.midoze.local.menu

import android.content.Context
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Menu

class MenuRepository(val context: Context) {
    val items = arrayListOf(
        Menu(
            icon = R.drawable.ic_firmware,
            title = context.resources.getString(R.string.menu_firmwares),
            tag = "Firmwares"
        ),
        Menu(
            icon = R.drawable.ic_watchface,
            title = context.resources.getString(R.string.menu_watchface),
            tag = "Watchface"
        ),
        Menu(
            icon = R.drawable.ic_apps,
            title = context.resources.getString(R.string.menu_apps),
            tag = "Apps"
        ),
        Menu(
            icon = R.drawable.ic_request,
            title = context.resources.getString(R.string.menu_request),
            tag = "Request"
        ),
        Menu(
            icon = R.drawable.ic_download,
            title = context.resources.getString(R.string.menu_downloads),
            tag = "Downloads"
        ),
        Menu(
            icon = R.drawable.ic_info,
            title = context.resources.getString(R.string.menu_github),
            tag = "About"
        ),
        Menu(
            icon = R.drawable.ic_delete,
            title = context.resources.getString(R.string.menu_uninstall),
            tag = "Uninstall"
        )
    )
}