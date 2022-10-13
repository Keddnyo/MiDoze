package io.github.keddnyo.midoze.ui.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoute(
    var title: String,
    var path: String,
    var icon: ImageVector
) {
    object News :
        NavigationRoute(
            "News",
            "news",
            Icons.TwoTone.Newspaper
        )

    object Apps :
        NavigationRoute(
            "Apps",
            "apps",
            Icons.TwoTone.Apps
        )

    object ROMs :
        NavigationRoute(
            "ROMs",
            "roms",
            Icons.TwoTone.Dns
        )

    object Dials :
        NavigationRoute(
            "Dials",
            "dials",
            Icons.TwoTone.ImagesearchRoller
        )
}