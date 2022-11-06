package io.github.keddnyo.midoze.ui.main.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.keddnyo.midoze.local.viewmodels.FirmwareViewModel
import io.github.keddnyo.midoze.ui.main.routes.MiDozeAppContent

class MainActivity : ComponentActivity() {

    private val firmwaresViewModel by viewModels<FirmwareViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiDozeAppContent(
                viewModel = firmwaresViewModel,
            )
        }

    }
}