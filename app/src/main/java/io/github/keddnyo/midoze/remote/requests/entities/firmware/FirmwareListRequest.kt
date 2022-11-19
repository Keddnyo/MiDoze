package io.github.keddnyo.midoze.remote.requests.entities.firmware

import io.github.keddnyo.midoze.internal.routeArray
import io.github.keddnyo.midoze.local.models.firmware.FirmwareWearable
import io.github.keddnyo.midoze.local.repositories.firmware.firmwareWearables
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

suspend fun getFirmwareList(
    i: Int
): ArrayList<Firmware> {
    val array = arrayListOf<Firmware>()

    array.run {
        routeArray.forEach { host ->
            // Making request
            getFirmware(
                host = host,
                firmwareWearable = firmwareWearables[i].run {
                    FirmwareWearable(
                        deviceName, devicePreview, deviceSource, productionSource, firmwareApplication
                    )
                }
            )?.apply {
                add(this)
                return@run
            }
        }
    }

    return array
}