package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): DeviceData {
        return when (deviceSource.toString()) {
            "12" -> DeviceData("Amazfit Bip", R.drawable.amazfit_bip)

            "20" -> DeviceData("Amazfit Bip 2", R.drawable.amazfit_bip_s) // TODO: May be replace
            "24" -> DeviceData("Xiaomi Mi Band 4 NFC", R.drawable.mi_band_4_nfc)
            "25" -> DeviceData("Xiaomi Mi Band 4 GL", R.drawable.mi_band_4)
            "28" -> DeviceData("Amazfit Bip S", R.drawable.amazfit_bip_s)
            "29" -> DeviceData("Amazfit Bip S Lite", R.drawable.amazfit_bip_s_lite)

            "30" -> DeviceData("Amazfit Verge Lite GL", R.drawable.amazfit_verge_lite)
            "31" -> DeviceData("Xiaomi Mi Band 3i", R.drawable.mi_band_3i)
            "35" -> DeviceData("Amazfit GTR 47mm CH", R.drawable.amazfit_gtr)
            "36" -> DeviceData("Amazfit GTR 47mm GL", R.drawable.amazfit_gtr)
            "37" -> DeviceData("Amazfit GTR 42mm CH", R.drawable.amazfit_gtr)
            "38" -> DeviceData("Amazfit GTR 42mm GL", R.drawable.amazfit_gtr)
            "39" -> DeviceData("Amazfit Bip Lite CH", R.drawable.amazfit_bip)

            "40" -> DeviceData("Amazfit GTS CH", R.drawable.amazfit_gts)
            "41" -> DeviceData("Amazfit GTS GL", R.drawable.amazfit_gts)
            "42" -> DeviceData("Amazfit Bip Lite GL", R.drawable.amazfit_bip)
            "44" -> DeviceData("Amazfit GTR 42mm Lite GL", R.drawable.amazfit_gtr) // TODO: May be replace
            "46" -> DeviceData("Amazfit GTR 47mm Lite GL", R.drawable.amazfit_gtr) // TODO: May be replace

            "50" -> "Amazfit T-Rex"
            "51" -> DeviceData("Amazfit GTR 42mm SWK", R.drawable.amazfit_gtr) // TODO: May be replace
            "52" -> DeviceData("Amazfit GTR 42mm SWK GL", R.drawable.amazfit_gtr) // TODO: May be replace
            "53" -> "Amazfit X CH"
            "54" -> DeviceData("Amazfit GTR 47mm Disney", R.drawable.amazfit_gtr) // TODO: May be replace
            "56" -> "Zepp Z CH"
            "57" -> "Zepp E Circle CH"
            "58" -> DeviceData("Xiaomi Mi Band 5 NFC", R.drawable.mi_band_5_nfc)
            "59" -> DeviceData("Xiaomi Mi Band 5", R.drawable.mi_band_5_nfc)

            "61" -> "Zepp E Square CH"
            "62" -> "Amazfit Neo"
            "63" -> "Amazfit GTR 2 CH"
            "64" -> "Amazfit GTR 2 GL"
            "67" -> "Amazfit Pop Pro"
            "68" -> "Amazfit Pop"
            "69" -> "Amazfit Bip U Pro"

            "70" -> "Amazfit Bip U"
            "71" -> "Amazfit X GL"
            "73" -> DeviceData("Amazfit Band 5", R.drawable.amazfit_band_5)
            "76" -> "Zepp Z GL"
            "77" -> "Amazfit GTS 2 CH"
            "78" -> "Amazfit GTS 2 GL"

            "81" -> "Zepp E Circle GL"
            "82" -> "Zepp E Square GL"
            "83" -> "Amazfit T-Rex Pro CH"

            "92" -> "Amazfit GTS 2 mini"
            "98" -> "Amazfit GTR 2 eSIM"

            "104" -> "Amazfit GTS 2 Mini"

            "113" -> DeviceData("Amazfit Bip", R.drawable.amazfit_bip)

            "200" -> "Amazfit T-Rex Pro GL"
            "206" -> "Amazfit GTR 2e CH"
            "207" -> "Amazfit GTS 2e CH"
            "209" -> "Amazfit GTR 2e GL"

            "210" -> "Amazfit GTS 2e GL"
            "211" -> DeviceData("Xiaomi Mi Band 6 NFC", R.drawable.mi_band_6)
            "212" -> DeviceData("Xiaomi Mi Band 6 GL", R.drawable.mi_band_6)

            "224" -> "Amazfit GTS 3 CH"
            "225" -> "Amazfit GTS 3 GL"
            "226" -> "Amazfit GTR 3 CH"
            "227" -> "Amazfit GTR 3 GL"
            "229" -> "Amazfit GTR 3 Pro CH"

            "230" -> "Amazfit GTR 3 Pro GL"

            "242" -> "Amazfit GTR 3 Pro Ltd"
            "243" -> "Amazfit GTR 3 Pro Ltd"
            "244" -> "Amazfit GTR 2"
            "245" -> "Amazfit GTS 2"

            "254" -> "Amazfit Band 7"
            "256" -> "Amazfit Bip 3 Pro"
            "257" -> "Amazfit Bip 3"

            "262" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 257
            "263" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 258
            "264" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 259
            "265" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 260

            else -> "Unknown $deviceSource $productionSource"
        }
    }
}