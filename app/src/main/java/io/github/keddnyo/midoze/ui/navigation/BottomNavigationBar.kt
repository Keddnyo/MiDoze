package io.github.keddnyo.midoze.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController,
    snackBarHostState: SnackbarHostState
) {
    val items = listOf(
        NavigationItem.Feed,
        NavigationItem.Apps,
        NavigationItem.Dials
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            val title = stringResource(id = item.title)

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    snackBarHostState.currentSnackbarData?.dismiss()

                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = title
                    )
                },
                alwaysShowLabel = true,
                label = {
                    Text(
                        text = title
                    )
                },
            )
        }
    }
}