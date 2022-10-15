package io.github.keddnyo.midoze.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.keddnyo.midoze.ui.routes.NavigationRoute
import io.github.keddnyo.midoze.ui.routes.apps.AppsRoute
import io.github.keddnyo.midoze.ui.routes.roms.ROMsRoute
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navHostController = rememberNavController()
    val title: MutableState<String?> = remember { mutableStateOf(null) }

    MiDozeTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Title(
                            title = title.value
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar(navHostController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                NavHost(
                    navController = navHostController,
                    startDestination = NavigationRoute.News.path
                ) {
                    composable(NavigationRoute.News.path) {
                        title.value = NavigationRoute.News.title
                    }
                    composable(NavigationRoute.Apps.path) {
                        title.value = NavigationRoute.Apps.title
                        AppsRoute()
                    }
                    composable(NavigationRoute.ROMs.path) {
                        title.value = NavigationRoute.ROMs.title
                        ROMsRoute()
                    }
                    composable(NavigationRoute.Dials.path) {
                        title.value = NavigationRoute.Dials.title
                    }
                }
            }
        }
    }

}