package io.github.keddnyo.midoze.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.keddnyo.midoze.local.view_models.FirmwareViewModel

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