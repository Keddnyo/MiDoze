package io.github.keddnyo.midoze.ui.main

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.keddnyo.midoze.ui.routes.NavigationRoute

@Composable
fun BottomAppBar(
    navHostController: NavHostController
) {
    BottomAppBar {

        // Navigation Bar Item Template
        @Composable
        fun NavigationBarItem(
            route: NavigationRoute
        ) {
            val backStack by navHostController.currentBackStackEntryAsState()
            val destination = backStack?.destination
            val currentRoute = destination?.route

            val isRouteSelected = currentRoute == route.path

            this.NavigationBarItem(
                label = { Text(route.title) },
                selected = isRouteSelected,
                onClick = {
                    navHostController.navigate(route.path) {
                        launchSingleTop = true
                        popUpTo(NavigationRoute.News.path)
                    }
                },
                icon = {
                    Icon(
                        route.icon,
                        route.title,
                    )
                }
            )
        }

        // Home tab
        NavigationBarItem(
            route = NavigationRoute.News,
        )

        // Apps tab
        NavigationBarItem(
            route = NavigationRoute.Apps,
        )

        // ROMs tab
        NavigationBarItem(
            route = NavigationRoute.ROMs,
        )

        // Dials tab
        NavigationBarItem(
            route = NavigationRoute.Dials,
        )

    }
}