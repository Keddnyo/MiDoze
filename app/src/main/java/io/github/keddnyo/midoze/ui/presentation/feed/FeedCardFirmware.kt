package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.runtime.Composable
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

@Composable
fun FeedCardFirmware(
    firmware: Firmware?
) {
    firmware?.run {
        FeedCard(
            icon = device.devicePreview,
            title = device.deviceName,
            subtitle1 = firmwareVersion?.let { "FW: $it" },
            subtitle2 = resourceVersion?.let { "RES: $it" },
            summary = changeLog,
            onClick = { }
        )
    }
}