package io.github.keddnyo.midoze.remote.requests

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.data_models.*

suspend fun getFirmwareList(
    indexRange: IntRange,
): ArrayList<FirmwareDataModel> {
    val firmwareList: ArrayList<FirmwareDataModel> = arrayListOf()

    val appArray: Array<WearableApplicationDataModel> = arrayOf(
        WearableApplicationDataModel(
            appName = "com.xiaomi.hm.health",
            appVersion = "6.3.5_50638",
            appProductIcon = R.drawable.ic_zepp_life,
            appProductName = ZEPP_LIFE_APP_NAME,
        ),
        WearableApplicationDataModel(
            appName = "com.huami.midong",
            appVersion = "7.2.0-play_100865",
            appProductIcon = R.drawable.ic_zepp,
            appProductName = ZEPP_APP_NAME,
        ),
    )

    val regionArray: Array<WearableRegionDataModel> = arrayOf(
        WearableRegionDataModel(
            lang = "en_US",
            country = "US",
        ),
        WearableRegionDataModel(
            lang = "zh_CH",
            country = "CH",
        ),
    )

    (indexRange).forEach { deviceSource ->
        (256..265).forEach { productionSource ->
            (appArray).forEach { application ->
                (regionArray).forEach { region ->

                    getFirmware(
                        device = WearableDeviceDataModel(
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