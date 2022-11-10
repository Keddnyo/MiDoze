package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.local.models.firmware.Device
import io.github.keddnyo.midoze.local.models.firmware.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware

suspend fun getFirmwareList(
    indexRange: IntRange,
): ArrayList<Firmware> {
    val firmwareList: ArrayList<Firmware> = arrayListOf()

//    val appArray = arrayOf(
//        Application(
//            instance = AppName.Zepp,
//            appVersion = AppVersion.Zepp,
//        ),
//        Application(
//            instance = AppName.ZeppLife,
//            appVersion = AppVersion.ZeppLife,
//        ),
//    )
//
//    val regionArray = arrayOf(
//        Region.UnitedStates, Region.Chinese,
//    )

    (indexRange).forEach { index ->
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
    }

    return firmwareList
}