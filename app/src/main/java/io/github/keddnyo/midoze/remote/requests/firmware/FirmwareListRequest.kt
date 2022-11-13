package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.local.repositories.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

suspend fun getFirmwareList(i: Int) = arrayListOf<Firmware>().run {
    getFirmware(
        // Get device data from deviceList repository by index
        deviceList[i].run {
            // Making request
            Device(
                deviceName, devicePreview, deviceSource, productionSource, application, region
            )
        }
    )?.apply {
        // Add firmware to array if response has returned not null value
        add(this)
    }
    // Return array
    this
}