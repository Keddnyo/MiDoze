package io.github.keddnyo.midoze.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccessTime
import androidx.compose.material.icons.twotone.Apps
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.keddnyo.midoze.R

open class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val title: Int
) {
    object Dials : NavigationItem(
        route = "dials",
        icon = Icons.TwoTone.AccessTime,
        title = R.string.dials
    )

    object Feed : NavigationItem(
        route = "feed",
        icon = Icons.TwoTone.Memory,
        title = R.string.roms
    )

    object Apps : NavigationItem(
        route = "apps",
        icon = Icons.TwoTone.Apps,
        title = R.string.apps
    )
}