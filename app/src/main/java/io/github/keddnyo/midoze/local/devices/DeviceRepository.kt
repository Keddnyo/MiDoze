package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): DeviceData {
        return when (deviceSource.toString()) {
            "12" -> DeviceData("Amazfit Bip", R.drawable.amazfit_bip)

            "20" -> DeviceData("Amazfit Bip S", R.drawable.amazfit_bip_s) // TODO: May be replace
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

            "50" -> DeviceData("Amazfit T-Rex", R.drawable.amazfit_t_rex)
            "51" -> DeviceData("Amazfit GTR 42mm SWK", R.drawable.amazfit_gtr) // TODO: May be replace
            "52" -> DeviceData("Amazfit GTR 42mm SWK GL", R.drawable.amazfit_gtr) // TODO: May be replace
            "53" -> DeviceData("Amazfit X CH", R.drawable.amazfit_x)
            "54" -> DeviceData("Amazfit GTR 47mm Disney", R.drawable.amazfit_gtr) // TODO: May be replace
            "56" -> DeviceData("Zepp Z CH", R.drawable.zepp_z)
            "57" -> DeviceData("Zepp E Circle CH", R.drawable.zepp_e_circle)
            "58" -> DeviceData("Xiaomi Mi Band 5 NFC", R.drawable.mi_band_5_nfc)
            "59" -> DeviceData("Xiaomi Mi Band 5", R.drawable.mi_band_5_nfc)

            "61" -> DeviceData("Zepp E Square CH", R.drawable.zepp_e_square)
            "62" -> DeviceData("Amazfit Neo", R.drawable.amazfit_neo)
            "63" -> DeviceData("Amazfit GTR 2 CH", R.drawable.amazfit_gtr)
            "64" -> DeviceData("Amazfit GTR 2 GL", R.drawable.amazfit_gtr)
            "67" -> DeviceData("Amazfit Pop Pro", R.drawable.amazfit_bip_u)
            "68" -> DeviceData("Amazfit Pop", R.drawable.amazfit_bip_u)
            "69" -> DeviceData("Amazfit Bip U Pro", R.drawable.amazfit_bip_u)

            "70" -> DeviceData("Amazfit Bip U", R.drawable.amazfit_bip_u)
            "71" -> DeviceData("Amazfit X GL", R.drawable.amazfit_x)
            "73" -> DeviceData("Amazfit Band 5", R.drawable.amazfit_band_5)
            "76" -> DeviceData("Zepp Z GL", R.drawable.zepp_z)
            "77" -> DeviceData("Amazfit GTS 2 CH", R.drawable.amazfit_gts_2)
            "78" -> DeviceData("Amazfit GTS 2 GL", R.drawable.amazfit_gts_2)

            "81" -> DeviceData("Zepp E Circle GL", R.drawable.zepp_e_circle)
            "82" -> DeviceData("Zepp E Square GL", R.drawable.zepp_e_square)
            "83" -> DeviceData("Amazfit T-Rex Pro CH", R.drawable.amazfit_t_rex) // TODO: May be replace

            "92" -> DeviceData("Amazfit GTS 2 mini", R.drawable.amazfit_gts_2_mini)
            "98" -> DeviceData("Amazfit GTR 2 eSIM", R.drawable.amazfit_gtr)

            "104" -> DeviceData("Amazfit GTS 2 Mini", R.drawable.amazfit_gts_2_mini)

            // "113" -> DeviceData("Amazfit Bip", R.drawable.amazfit_bip)

            "200" -> DeviceData("Amazfit T-Rex Pro GL", R.drawable.amazfit_t_rex) // TODO: May be replace
            "206" -> DeviceData("Amazfit GTR 2e CH", R.drawable.amazfit_gtr)
            "207" -> DeviceData("Amazfit GTS 2e CH", R.drawable.amazfit_gts_2)
            "209" -> DeviceData("Amazfit GTR 2e GL", R.drawable.amazfit_gtr)

            "210" -> DeviceData("Amazfit GTS 2e GL", R.drawable.amazfit_gts_2)
            "211" -> DeviceData("Xiaomi Mi Band 6 NFC", R.drawable.mi_band_6)
            "212" -> DeviceData("Xiaomi Mi Band 6 GL", R.drawable.mi_band_6)

            "224" -> DeviceData("Amazfit GTS 3 CH", R.drawable.amazfit_gts_3)
            "225" -> DeviceData("Amazfit GTS 3 GL", R.drawable.amazfit_gts_3)
            "226" -> DeviceData("Amazfit GTR 3 CH", R.drawable.amazfit_gtr_3)
            "227" -> DeviceData("Amazfit GTR 3 GL", R.drawable.amazfit_gtr_3)
            "229" -> DeviceData("Amazfit GTR 3 Pro CH", R.drawable.amazfit_gtr_3)

            "230" -> DeviceData("Amazfit GTR 3 Pro GL", R.drawable.amazfit_gtr_3)

            "242" -> DeviceData("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "243" -> DeviceData("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "244" -> DeviceData("Amazfit GTR 2", R.drawable.amazfit_gtr)
            "245" -> DeviceData("Amazfit GTS 2", R.drawable.amazfit_gts_2)

            "254" -> DeviceData("Amazfit Band 7", R.drawable.amazfit_band_5) // TODO: May be replace
            "256" -> DeviceData("Amazfit Bip 3 Pro", R.drawable.amazfit_bip_3)
            "257" -> DeviceData("Amazfit Bip 3", R.drawable.amazfit_bip_3)

            "262" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 257
            "263" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 258
            "264" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 259
            "265" -> DeviceData("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 260

            else -> DeviceData("Unknown $deviceSource $productionSource", R.drawable.unknown)
        }
    }
}