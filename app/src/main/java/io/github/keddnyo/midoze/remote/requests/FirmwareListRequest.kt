package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.local.models.firmware.Application
import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.local.repository.WearableApplication
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

suspend fun getFirmwareList(
    indexRange: IntRange,
): ArrayList<Firmware> {
    val firmwareList: ArrayList<Firmware> = arrayListOf()

    val appArray = arrayOf(
        Application(
            instance = WearableApplication.Zepp,
            appVersion = "7.2.0-play_100865",
        ),
        Application(
            instance = WearableApplication.ZeppLife,
            appVersion = "6.3.5_50638",
        ),
    )

    (indexRange).forEach deviceSource@ { deviceSource ->
        (256..258).forEach { productionSource ->
            (appArray).forEach { application ->
                getFirmware(
                    device = Device(
                        deviceSource = deviceSource,
                        productionSource = productionSource,
                        application = application,
                    )
                )?.let { firmware ->
                    firmwareList.add(firmware)
                    return@deviceSource
                }
            }
        }
    }

    return firmwareList
}