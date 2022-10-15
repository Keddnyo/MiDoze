package io.github.keddnyo.midoze.remote.requests.firmware

import io.github.keddnyo.midoze.local.data_models.firmware.Firmware
import io.github.keddnyo.midoze.local.data_models.firmware.FirmwareStack
import io.github.keddnyo.midoze.local.repositories.firmware.wearableDevices

suspend fun getFirmwareStack(): ArrayList<FirmwareStack> {
    val firmwareStackArray: ArrayList<FirmwareStack> = arrayListOf()

    wearableDevices.forEach { stack ->
        val firmwareArray: ArrayList<Firmware> = arrayListOf()

        stack.stack.forEach { wearableDevice ->
            getFirmware(wearableDevice)?.let { firmware ->
                firmwareArray.add(firmware)
            }
        }

        firmwareStackArray.add(
            FirmwareStack(
                name = "Name",
                stack = firmwareArray,
            )
        )
    }

    return firmwareStackArray
}