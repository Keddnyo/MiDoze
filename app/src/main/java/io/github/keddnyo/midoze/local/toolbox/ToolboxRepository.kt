package io.github.keddnyo.midoze.local.toolbox

import android.content.Context
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Toolbox

class ToolboxRepository(val context: Context) {
    val items = arrayListOf(
        Toolbox(
            title = context.resources.getString(R.string.menu_custom_request),
            icon = R.drawable.ic_request,
            tag = "Request"
        ),
        Toolbox(
            title = context.resources.getString(R.string.app_downloads),
            icon = R.drawable.ic_download,
            tag = "Downloads"
        ),
        Toolbox(
            title = context.resources.getString(R.string.app_uninstall),
            icon = R.drawable.ic_uninstall,
            tag = "Uninstall"
        )
    )
}