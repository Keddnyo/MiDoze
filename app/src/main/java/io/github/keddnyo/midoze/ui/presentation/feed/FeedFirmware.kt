package io.github.keddnyo.midoze.ui.presentation.feed

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareDownload
import io.github.keddnyo.midoze.remote.models.firmware.FirmwareType
import io.github.keddnyo.midoze.remote.requests.download.downloadFirmware

@Composable
fun FeedCardFirmware(
    firmware: Firmware?
) {
    firmware?.run {
        val context = LocalContext.current
        val firmwareFileLinkArray = arrayListOf<FirmwareDownload>()

        firmwareUrl?.let { firmwareUrl ->
            firmwareFileLinkArray.add(
                FirmwareDownload(
                    fileType = FirmwareType.Firmware,
                    fileVersion = firmwareVersion.toString(),
                    url = firmwareUrl
                )
            )
        }
        resourceUrl?.let { resourceUrl ->
            firmwareFileLinkArray.add(
                FirmwareDownload(
                    fileType = FirmwareType.Resource,
                    fileVersion = resourceVersion.toString(),
                    url = resourceUrl
                )
            )
        }
        baseResourceUrl?.let { baseResourceUrl ->
            firmwareFileLinkArray.add(
                FirmwareDownload(
                    fileType = FirmwareType.BaseResource,
                    fileVersion = baseResourceVersion.toString(),
                    url = baseResourceUrl
                )
            )
        }
        fontUrl?.let { fontUrl ->
            firmwareFileLinkArray.add(
                FirmwareDownload(
                    fileType = FirmwareType.Font,
                    fileVersion = fontVersion.toString(),
                    url = fontUrl
                )
            )
        }
        gpsUrl?.let { gpsUrl ->
            firmwareFileLinkArray.add(
                FirmwareDownload(
                    fileType = FirmwareType.Gps,
                    fileVersion = gpsVersion.toString(),
                    url = gpsUrl
                )
            )
        }

        FeedCard(
            title = firmwareWearable.deviceName,
            subtitle = firmwareVersion,
            icon = firmwareWearable.devicePreview,
            summary = changeLog,
            onClick = {
                firmwareFileLinkArray.forEach { firmwareFile ->
                    downloadFirmware(
                        context = context,
                        deviceName = firmwareWearable.deviceName,
                        firmwareDownload = firmwareFile
                    )
                }
            }
        )
    }
}