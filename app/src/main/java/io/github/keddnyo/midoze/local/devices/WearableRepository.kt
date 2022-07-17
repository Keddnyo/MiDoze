package io.github.keddnyo.midoze.local.devices

import android.content.Context
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.Region
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.utils.PackageUtils

class WearableRepository(val context: Context, private val region: Region) {
    private fun addWearable(
        deviceSource: String,
        productionSource: String,
        isZeppLife: Boolean,
        isChineseRegion: Boolean
    ): Wearable {
        return Wearable(
            deviceSource = deviceSource,
            productionSource = productionSource,
            application = if (isZeppLife) {
                Application(
                    name = ZEPP_LIFE_NAME,
                    version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME)
                )
            } else {
                Application(
                    name = ZEPP_NAME,
                    version = PackageUtils().getPackageVersion(context, ZEPP_NAME)
                )
            },
            region = if (isChineseRegion) {
                Region(
                    country = "CH",
                    language = "zh_CH"
                )
            } else {
                Region(
                    country = region.country,
                    language = region.language
                )
            }
        )
    }

    val wearables = arrayListOf(
        // Amazfit Bip (CH) / Zepp Life
        addWearable(
            deviceSource = "12",
            productionSource = "256",
            isZeppLife = true,
            isChineseRegion = true
        ),

        // Amazfit Bip S / Zepp
        addWearable(
            deviceSource = "20",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Mi Band 4 NFC / Zepp Life
        addWearable(
            deviceSource = "24",
            productionSource = "256",
            isZeppLife = true,
            isChineseRegion = false
        ),

        // Mi Band 4 GL / Zepp Life (Zepp)
        addWearable(
            deviceSource = "25",
            productionSource = "257",
            isZeppLife = true,
            isChineseRegion = false
        ),

        // Amazfit Bip S / Zepp
        addWearable(
            deviceSource = "28",
            productionSource = "258",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Bip S Lite / Zepp
        addWearable(
            deviceSource = "29",
            productionSource = "259",
            isZeppLife = false,
            isChineseRegion = true
        ),

        // Amazfit Verge Lie GL / Zepp (Zepp Life)
        addWearable(
            deviceSource = "30",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Mi Band 3i / Zepp Life
        addWearable(
            deviceSource = "31",
            productionSource = "256",
            isZeppLife = true,
            isChineseRegion = false
        ),

        // Amazfit GTR 47 CH / Zepp
        addWearable(
            deviceSource = "35",
            productionSource = "256",
            isZeppLife = true,
            isChineseRegion = false
        ),
    )
}