package io.github.keddnyo.midoze.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.keddnyo.midoze.local.viewmodels.firmware.FirmwareViewModel
import io.github.keddnyo.midoze.local.viewmodels.watchface.WatchfaceViewModel

class MainActivity : ComponentActivity() {

    private val feedViewModel by viewModels<FirmwareViewModel>()
    private val dialViewModel by viewModels<WatchfaceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiDozeAppContent(
                feedViewModel = feedViewModel,
                dialViewModel = dialViewModel
            )
        }

    }
}