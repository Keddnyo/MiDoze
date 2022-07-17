package io.github.keddnyo.midoze.local.devices

import android.content.Context
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.Region
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.utils.LanguageUtils
import io.github.keddnyo.midoze.utils.PackageUtils

class WearableRepository(val context: Context) {
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
                    country = LanguageUtils().currentCountry,
                    language = LanguageUtils().currentLanguage
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
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 47 GL / Zepp
        addWearable(
            deviceSource = "36",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 42 CH / Zepp
        addWearable(
            deviceSource = "37",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 42 GL / Zepp
        addWearable(
            deviceSource = "38",
            productionSource = "257",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Bip lite CH / Zepp Life (Zepp)
        addWearable(
            deviceSource = "39",
            productionSource = "256",
            isZeppLife = true,
            isChineseRegion = false
        ),

        // Amazfit GTS CH / Zepp
        addWearable(
            deviceSource = "40",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTS GL / Zepp
        addWearable(
            deviceSource = "41",
            productionSource = "257",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Bip Lite GL / Zepp Life (Zepp)
        addWearable(
            deviceSource = "42",
            productionSource = "257",
            isZeppLife = true,
            isChineseRegion = true
        ),

        // Amazfit GTR 47 Lite GL / Zepp
        addWearable(
            deviceSource = "46",
            productionSource = "258",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit T-Rex / Zepp
        addWearable(
            deviceSource = "50",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 42 SWK / Zepp
        addWearable(
            deviceSource = "51",
            productionSource = "260",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 42 SWK GL / Zepp
        addWearable(
            deviceSource = "52",
            productionSource = "261",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit X CH / Zepp
        addWearable(
            deviceSource = "53",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 47 Disney / Zepp
        addWearable(
            deviceSource = "54",
            productionSource = "259",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Zepp Z CH / Zepp
        addWearable(
            deviceSource = "56",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Zepp E Circle CH / Zepp
        addWearable(
            deviceSource = "57",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Mi Band 5 NFC / Zepp Life
        addWearable(
            deviceSource = "58",
            productionSource = "256",
            isZeppLife = true,
            isChineseRegion = false
        ),

        // Mi Band 5 / Zepp Life
        addWearable(
            deviceSource = "59",
            productionSource = "257",
            isZeppLife = true,
            isChineseRegion = false
        ),

        // Zepp E Square CH / Zepp Life
        addWearable(
            deviceSource = "61",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Neo / Zepp Life
        addWearable(
            deviceSource = "62",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 2 CH / Zepp Life
        addWearable(
            deviceSource = "63",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit GTR 2 GL / Zepp Life
        addWearable(
            deviceSource = "64",
            productionSource = "257",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Ares / Zepp Life
        addWearable(
            deviceSource = "65",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Pop Pro / Zepp Life
        addWearable(
            deviceSource = "67",
            productionSource = "256",
            isZeppLife = false,
            isChineseRegion = false
        ),

        // Amazfit Pop / Zepp Life
        addWearable(
            deviceSource = "68",
            productionSource = "258",
            isZeppLife = false,
            isChineseRegion = true
        ),

        // Amazfit Bip U Pro / Zepp Life
        addWearable(
            deviceSource = "69",
            productionSource = "257",
            isZeppLife = false,
            isChineseRegion = false
        ),
    )
}