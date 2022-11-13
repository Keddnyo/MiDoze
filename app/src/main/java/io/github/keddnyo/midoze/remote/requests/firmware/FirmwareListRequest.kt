package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.internal.routeArray
import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.local.repositories.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

suspend fun getFirmwareList(
    i: Int
) = arrayListOf<Firmware>().run {
    run loop@ {
        routeArray.forEach { host ->
            // Making request
            getFirmware(
                host = host,
                device = deviceList[i].run {
                    Device(
                        deviceName, devicePreview, deviceSource, productionSource, application, region
                    )
                }
            )?.apply {
                add(this)
                return@loop
            }
        }
    }
    // Return array
    this
}