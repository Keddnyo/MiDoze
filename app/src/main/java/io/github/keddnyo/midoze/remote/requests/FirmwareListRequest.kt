package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.local.models.firmware.Application
import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.local.repository.WearableRegion
import io.github.keddnyo.midoze.local.repository.Zepp
import io.github.keddnyo.midoze.local.repository.ZeppLife

suspend fun getFirmwareList(
    indexRange: IntRange,
): ArrayList<Firmware> {
    val firmwareList: ArrayList<Firmware> = arrayListOf()

    val appArray = arrayOf(
        Application(
            appName = Zepp,
            appVersion = "7.2.0-play_100865",
        ),
        Application(
            appName = ZeppLife,
            appVersion = "6.3.5_50638",
        ),
    )

    val regionArray = arrayOf(
        WearableRegion.STATES, WearableRegion.CHINESE,
    )

    (indexRange).forEach { deviceSource ->
        (256..265).forEach { productionSource ->
            (appArray).forEach { application ->
                (regionArray).forEach { region ->

                    getFirmware(
                        device = Device(
                            deviceSource = deviceSource,
                            productionSource = productionSource,
                            application = application,
                            region = region,
                        )
                    )?.let { firmware ->
                        firmwareList.add(firmware)
                    }

                }
            }
        }
    }

    return firmwareList
}