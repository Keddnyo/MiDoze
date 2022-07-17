package io.github.keddnyo.midoze.local.devices

import android.content.Context
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.Region
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.utils.PackageUtils

class WearableRepository(context: Context, region: Region) {
    val wearables = arrayListOf(
        // Amazfit Bip (CH) / Zepp Life
        Wearable(
            deviceSource = "12",
            productionSource = "256",
            application = Application(
                name = ZEPP_LIFE_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME)
            ),
            region = Region(
                country = "CH",
                language = "zh_CH"
            )
        ),

        // Amazfit Bip S / Zepp
        Wearable(
            deviceSource = "20",
            productionSource = "256",
            application = Application(
                name = ZEPP_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.language
            )
        ),

        // Mi Band 4 NFC / Zepp Life
        Wearable(
            deviceSource = "24",
            productionSource = "256",
            application = Application(
                name = ZEPP_LIFE_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.country
            )
        ),

        // Mi Band 4 GL / Zepp Life (Zepp)
        Wearable(
            deviceSource = "25",
            productionSource = "257",
            application = Application(
                name = ZEPP_LIFE_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.country
            )
        ),

        // Amazfit Bip S / Zepp
        Wearable(
            deviceSource = "28",
            productionSource = "258",
            application = Application(
                name = ZEPP_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.country
            )
        ),

        // Amazfit Bip S Lite / Zepp
        Wearable(
            deviceSource = "29",
            productionSource = "259",
            application = Application(
                name = ZEPP_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_NAME)
            ),
            region = Region(
                country = "CH",
                language = "zh_CH"
            )
        ),

        // Amazfit Verge Lie GL / Zepp (Zepp Life)
        Wearable(
            deviceSource = "30",
            productionSource = "256",
            application = Application(
                name = ZEPP_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.language
            )
        ),

        // Mi Band 3i / Zepp Life
        Wearable(
            deviceSource = "31",
            productionSource = "256",
            application = Application(
                name = ZEPP_LIFE_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.language
            )
        ),

        // Amazfit GTR 47 CH / Zepp
        Wearable(
            deviceSource = "35",
            productionSource = "256",
            application = Application(
                name = ZEPP_NAME,
                version = PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME)
            ),
            region = Region(
                country = region.country,
                language = region.language
            )
        )
    )
}