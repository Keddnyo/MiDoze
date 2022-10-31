package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import io.github.keddnyo.midoze.local.data_models.WearableApplicationDataModel
import io.github.keddnyo.midoze.local.data_models.WearableDeviceDataModel
import io.github.keddnyo.midoze.local.data_models.WearableRegionDataModel

suspend fun getFirmwareList(
    startIndex: Int,
    endIndex: Int,
): ArrayList<FirmwareDataModel> {
    val firmwareList: ArrayList<FirmwareDataModel> = arrayListOf()

    (startIndex..endIndex).forEach { deviceSource ->
        getFirmware(
            device = WearableDeviceDataModel(
                deviceSource = deviceSource,
                productionSource = 256,
                application = WearableApplicationDataModel(
                    appName = "com.huami.midong",
                    appVersion = "7.2.0-play_100865",
                ),
                region = WearableRegionDataModel(
                    lang = "en_US",
                    country = "US",
                )
            )
        )?.let { firmware ->
            firmwareList.add(firmware)
        }
    }

    return firmwareList
}