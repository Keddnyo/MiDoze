package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.local.repository.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

suspend fun getFirmwareList(
    index: Int,
): ArrayList<Firmware> {
    val firmwareList = arrayListOf<Firmware>()

    getFirmware(
        deviceList[index].run {
            Device(
                deviceName,
                devicePreview,
                deviceSource,
                productionSource,
                application,
                region,
            )
        }
    )?.let { firmware ->
        firmwareList.add(firmware)
    }

    return firmwareList
}