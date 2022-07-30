package io.github.keddnyo.midoze.local.devices

import android.content.Context
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.Region
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.dataModels.WearableStack
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_PACKAGE_NAME
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
                    version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_PACKAGE_NAME)
                )
            } else {
                Application(
                    name = ZEPP_NAME,
                    version = PackageUtils().getPackageVersion(context, ZEPP_PACKAGE_NAME)
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
        // Amazfit Bands
        WearableStack(
            name = "Amazfit Band",
            wearableStack = arrayListOf(
                // Amazfit Band 5 / Zepp
                addWearable(
                    deviceSource = "73",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit Band 7 / Zepp
                addWearable(
                    deviceSource = "254",
                    productionSource = "259",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Xiaomi Mi Band
        WearableStack(
            name = "Xiaomi Mi Band",
            wearableStack = arrayListOf(
                // Mi Band 3i / Zepp Life
                addWearable(
                    deviceSource = "31",
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

                // Mi Band 4 NFC / Zepp Life
                addWearable(
                    deviceSource = "24",
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

                // Mi Band 5 NFC / Zepp Life
                addWearable(
                    deviceSource = "58",
                    productionSource = "256",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 6 GL / Zepp
                addWearable(
                    deviceSource = "212",
                    productionSource = "257",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 6 NFC / Zepp
                addWearable(
                    deviceSource = "211",
                    productionSource = "256",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 7 / Zepp Life
                addWearable(
                    deviceSource = "260",
                    productionSource = "256",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 7 / Zepp Life
                addWearable(
                    deviceSource = "262",
                    productionSource = "258",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 7 / Zepp Life
                addWearable(
                    deviceSource = "263",
                    productionSource = "259",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 7 / Zepp Life
                addWearable(
                    deviceSource = "264",
                    productionSource = "260",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Mi Band 7 / Zepp Life
                addWearable(
                    deviceSource = "265",
                    productionSource = "261",
                    isZeppLife = true,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit Ares
        WearableStack(
            name = "Amazfit Ares",
            wearableStack = arrayListOf(
                // Amazfit Ares / Zepp
                addWearable(
                    deviceSource = "65",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = true
                )
            )
        ),

        // Amazfit Bip
        WearableStack(
            name = "Amazfit Bip",
            wearableStack = arrayListOf(
                // Amazfit Bip (CH) / Zepp Life
                addWearable(
                    deviceSource = "12",
                    productionSource = "256",
                    isZeppLife = true,
                    isChineseRegion = true
                ),

                // Amazfit Bip Lite CH / Zepp Life (Zepp)
                addWearable(
                    deviceSource = "39",
                    productionSource = "256",
                    isZeppLife = true,
                    isChineseRegion = false
                ),

                // Amazfit Bip Lite GL / Zepp Life (Zepp)
                addWearable(
                    deviceSource = "42",
                    productionSource = "257",
                    isZeppLife = true,
                    isChineseRegion = true
                ),

                // Amazfit Bip S CH / Zepp
                addWearable(
                    deviceSource = "20",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit Bip S GL / Zepp
                addWearable(
                    deviceSource = "28",
                    productionSource = "258",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit Bip S Lite GL / Zepp
                addWearable(
                    deviceSource = "29",
                    productionSource = "259",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit Bip 3 / Zepp Life
                addWearable(
                    deviceSource = "257",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = true
                ),

                // Amazfit Bip 3 Pro / Zepp Life
                addWearable(
                    deviceSource = "256",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = true
                ),
            )
        ),

        // Amazfit Pop
        WearableStack(
            name = "Amazfit Pop",
            wearableStack = arrayListOf(
                // Amazfit Pop / Zepp
                addWearable(
                    deviceSource = "68",
                    productionSource = "258",
                    isZeppLife = false,
                    isChineseRegion = true
                ),

                // Amazfit Pop Pro / Zepp
                addWearable(
                    deviceSource = "67",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = true
                ),

                // Amazfit Bip U / Zepp
                addWearable(
                    deviceSource = "70",
                    productionSource = "259",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit Bip U Pro / Zepp
                addWearable(
                    deviceSource = "69",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit GTR Watches
        WearableStack(
            name = "Amazfit GTR",
            wearableStack = arrayListOf(
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

                // Amazfit GTR 47 Disney / Zepp
                addWearable(
                    deviceSource = "54",
                    productionSource = "259",
                    isZeppLife = false,
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

                // Amazfit GTR 47 Lite GL / Zepp
                addWearable(
                    deviceSource = "46",
                    productionSource = "258",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 2 / Zepp
                addWearable(
                    deviceSource = "244",
                    productionSource = "258",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 2 CH / Zepp // TODO: Not found
                addWearable(
                    deviceSource = "63",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 2 GL / Zepp
                addWearable(
                    deviceSource = "64",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 2e CH / Zepp
                addWearable(
                    deviceSource = "206",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 2e GL / Zepp
                addWearable(
                    deviceSource = "209",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 2 eSIM / Zepp
                addWearable(
                    deviceSource = "98",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 3 CH / Zepp
                addWearable(
                    deviceSource = "226",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 3 GL / Zepp
                addWearable(
                    deviceSource = "227",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 3 Pro CH / Zepp
                addWearable(
                    deviceSource = "229",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 3 Pro GL / Zepp
                addWearable(
                    deviceSource = "230",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTR 3 Pro Ltd / Zepp
                addWearable(
                    deviceSource = "242",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit GTS Watches
        WearableStack(
            name = "Amazfit GTS",
            wearableStack = arrayListOf(
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

                // Amazfit GTS 2 / Zepp
                addWearable(
                    deviceSource = "245",
                    productionSource = "258",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 2 CH / Zepp // TODO: Not found
                addWearable(
                    deviceSource = "77",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 2 GL / Zepp
                addWearable(
                    deviceSource = "78",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 2 Mini CH / Zepp
                addWearable(
                    deviceSource = "91",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = true
                ),

                // Amazfit GTS 2 Mini GL / Zepp
                addWearable(
                    deviceSource = "92",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 2 Mini 2022 / Zepp
                addWearable(
                    deviceSource = "243",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 2e CH / Zepp
                addWearable(
                    deviceSource = "207",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 2e GL / Zepp
                addWearable(
                    deviceSource = "210",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 3 CH / Zepp
                addWearable(
                    deviceSource = "224",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 3 GL / Zepp
                addWearable(
                    deviceSource = "225",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 4 Mini CH / Zepp
                addWearable(
                    deviceSource = "246",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit GTS 4 Mini GL / Zepp
                addWearable(
                    deviceSource = "247",
                    productionSource = "259",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit Neo
        WearableStack(
            name = "Amazfit Neo",
            wearableStack = arrayListOf(
                // Amazfit Neo / Zepp
                addWearable(
                    deviceSource = "62",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit T-Rex
        WearableStack(
            name = "Amazfit T-Rex",
            wearableStack = arrayListOf(
                // Amazfit T-Rex / Zepp
                addWearable(
                    deviceSource = "50",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit T-Rex 2 CH / Zepp
                addWearable(
                    deviceSource = "418",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit T-Rex 2 GL / Zepp
                addWearable(
                    deviceSource = "419",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit T-Rex Pro CH / Zepp
                addWearable(
                    deviceSource = "83",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit T-Rex Pro GL / Zepp
                addWearable(
                    deviceSource = "200",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit Verge
        WearableStack(
            name = "Amazfit Verge",
            wearableStack = arrayListOf(
                // Amazfit Verge Lie GL / Zepp (Zepp Life)
                addWearable(
                    deviceSource = "30",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit X
        WearableStack(
            name = "Amazfit X",
            wearableStack = arrayListOf(
                // Amazfit X CH / Zepp
                addWearable(
                    deviceSource = "53",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Amazfit X GL / Zepp
                addWearable(
                    deviceSource = "71",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Zepp E
        WearableStack(
            name = "Zepp E",
            wearableStack = arrayListOf(
                // Zepp E Circle CH / Zepp
                addWearable(
                    deviceSource = "57",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Zepp E Circle GL / Zepp
                addWearable(
                    deviceSource = "81",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Zepp E Square CH / Zepp
                addWearable(
                    deviceSource = "61",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Zepp E Square GL / Zepp
                addWearable(
                    deviceSource = "82",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Zepp Z
        WearableStack(
            name = "Zepp Z",
            wearableStack = arrayListOf(
                // Zepp Z CH / Zepp
                addWearable(
                    deviceSource = "56",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                ),

                // Zepp Z GL / Zepp
                addWearable(
                    deviceSource = "76",
                    productionSource = "257",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        ),

        // Amazfit Scale
        WearableStack(
            name = "Amazfit Scale",
            wearableStack = arrayListOf(
                // Amazfit Smart Scale / Zepp
                addWearable(
                    deviceSource = "104",
                    productionSource = "256",
                    isZeppLife = false,
                    isChineseRegion = false
                )
            )
        )
    )
}