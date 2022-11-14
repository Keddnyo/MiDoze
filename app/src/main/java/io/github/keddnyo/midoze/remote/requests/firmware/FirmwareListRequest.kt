package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.internal.routeArray
import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.local.repositories.deviceList
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
                device = deviceList[i].run {
                    Device(
                        deviceName, devicePreview, deviceSource, productionSource, application
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