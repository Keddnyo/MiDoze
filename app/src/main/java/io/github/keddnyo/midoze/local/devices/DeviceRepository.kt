package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Wearable

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): Wearable {
        return when (deviceSource.toString()) {
            "12" -> Wearable("Amazfit Bip", R.drawable.amazfit_bip)

            "20" -> Wearable("Amazfit Bip S", R.drawable.amazfit_bip) // TODO: May be replace
            "24" -> Wearable("Xiaomi Mi Band 4 NFC", R.drawable.mi_band_4_nfc)
            "25" -> Wearable("Xiaomi Mi Band 4 GL", R.drawable.mi_band_4)
            "28" -> Wearable("Amazfit Bip S", R.drawable.amazfit_bip)
            "29" -> Wearable("Amazfit Bip S Lite", R.drawable.amazfit_bip_s_lite)

            "30" -> Wearable("Amazfit Verge Lite GL", R.drawable.amazfit_verge_lite)
            "31" -> Wearable("Xiaomi Mi Band 3i", R.drawable.mi_band_3i)
            "35" -> Wearable("Amazfit GTR 47 CH", R.drawable.amazfit_gtr)
            "36" -> Wearable("Amazfit GTR 47 GL", R.drawable.amazfit_gtr)
            "37" -> Wearable("Amazfit GTR 42 CH", R.drawable.amazfit_gtr)
            "38" -> Wearable("Amazfit GTR 42 GL", R.drawable.amazfit_gtr)
            "39" -> Wearable("Amazfit Bip Lite CH", R.drawable.amazfit_bip)

            "40" -> Wearable("Amazfit GTS CH", R.drawable.amazfit_gts)
            "41" -> Wearable("Amazfit GTS GL", R.drawable.amazfit_gts)
            "42" -> Wearable("Amazfit Bip Lite GL", R.drawable.amazfit_bip)
            "44" -> Wearable("Amazfit GTR 42 Lite GL", R.drawable.amazfit_gtr) // TODO: May be replace
            "46" -> Wearable("Amazfit GTR 47 Lite GL", R.drawable.amazfit_gtr) // TODO: May be replace

            "50" -> Wearable("Amazfit T-Rex", R.drawable.amazfit_t_rex)
            "51" -> Wearable("Amazfit GTR 42 SWK", R.drawable.amazfit_gtr) // TODO: May be replace
            "52" -> Wearable("Amazfit GTR 42 SWK GL", R.drawable.amazfit_gtr) // TODO: May be replace
            "53" -> Wearable("Amazfit X CH", R.drawable.amazfit_x)
            "54" -> Wearable("Amazfit GTR 47 Disney", R.drawable.amazfit_gtr) // TODO: May be replace
            "56" -> Wearable("Zepp Z CH", R.drawable.zepp_z)
            "57" -> Wearable("Zepp E Circle CH", R.drawable.zepp_e_circle)
            "58" -> Wearable("Xiaomi Mi Band 5 NFC", R.drawable.mi_band_5_nfc)
            "59" -> Wearable("Xiaomi Mi Band 5", R.drawable.mi_band_5_nfc)

            "61" -> Wearable("Zepp E Square CH", R.drawable.zepp_e_square)
            "62" -> Wearable("Amazfit Neo", R.drawable.amazfit_neo)
            "63" -> Wearable("Amazfit GTR 2 CH", R.drawable.amazfit_gtr)
            "64" -> Wearable("Amazfit GTR 2 GL", R.drawable.amazfit_gtr)
            "67" -> Wearable("Amazfit Pop Pro", R.drawable.amazfit_bip_u)
            "68" -> Wearable("Amazfit Pop", R.drawable.amazfit_bip_u)
            "69" -> Wearable("Amazfit Bip U Pro", R.drawable.amazfit_bip_u)

            "70" -> Wearable("Amazfit Bip U", R.drawable.amazfit_bip_u)
            "71" -> Wearable("Amazfit X GL", R.drawable.amazfit_x)
            "73" -> Wearable("Amazfit Band 5", R.drawable.amazfit_band_5)
            "76" -> Wearable("Zepp Z GL", R.drawable.zepp_z)
            "77" -> Wearable("Amazfit GTS 2 CH", R.drawable.amazfit_gts_2)
            "78" -> Wearable("Amazfit GTS 2 GL", R.drawable.amazfit_gts_2)

            "81" -> Wearable("Zepp E Circle GL", R.drawable.zepp_e_circle)
            "82" -> Wearable("Zepp E Square GL", R.drawable.zepp_e_square)
            "83" -> Wearable("Amazfit T-Rex Pro CH", R.drawable.amazfit_t_rex) // TODO: May be replace

            "92" -> Wearable("Amazfit GTS 2 mini", R.drawable.amazfit_gts_2_mini)
            "98" -> Wearable("Amazfit GTR 2 eSIM", R.drawable.amazfit_gtr)

            "104" -> Wearable("Amazfit GTS 2 Mini", R.drawable.amazfit_gts_2_mini)

            // "113" -> DeviceData("Amazfit Bip", R.drawable.amazfit_bip)

            "200" -> Wearable("Amazfit T-Rex Pro GL", R.drawable.amazfit_t_rex) // TODO: May be replace
            "206" -> Wearable("Amazfit GTR 2e CH", R.drawable.amazfit_gtr)
            "207" -> Wearable("Amazfit GTS 2e CH", R.drawable.amazfit_gts_2)
            "209" -> Wearable("Amazfit GTR 2e GL", R.drawable.amazfit_gtr)

            "210" -> Wearable("Amazfit GTS 2e GL", R.drawable.amazfit_gts_2)
            "211" -> Wearable("Xiaomi Mi Band 6 NFC", R.drawable.mi_band_6)
            "212" -> Wearable("Xiaomi Mi Band 6 GL", R.drawable.mi_band_6)

            "224" -> Wearable("Amazfit GTS 3 CH", R.drawable.amazfit_gts_3)
            "225" -> Wearable("Amazfit GTS 3 GL", R.drawable.amazfit_gts_3)
            "226" -> Wearable("Amazfit GTR 3 CH", R.drawable.amazfit_gtr_3)
            "227" -> Wearable("Amazfit GTR 3 GL", R.drawable.amazfit_gtr_3)
            "229" -> Wearable("Amazfit GTR 3 Pro CH", R.drawable.amazfit_gtr_3)

            "230" -> Wearable("Amazfit GTR 3 Pro GL", R.drawable.amazfit_gtr_3)

            "242" -> Wearable("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "243" -> Wearable("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "244" -> Wearable("Amazfit GTR 2", R.drawable.amazfit_gtr)
            "245" -> Wearable("Amazfit GTS 2", R.drawable.amazfit_gts_2)

            "254" -> Wearable("Amazfit Band 7", R.drawable.amazfit_band_5) // TODO: May be replace
            "256" -> Wearable("Amazfit Bip 3 Pro", R.drawable.amazfit_bip_3)
            "257" -> Wearable("Amazfit Bip 3", R.drawable.amazfit_bip_3)

            "262" -> Wearable("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 257
            "263" -> Wearable("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 258
            "264" -> Wearable("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 259
            "265" -> Wearable("Xiaomi Smart Band 7", R.drawable.mi_band_7) // 260

            else -> Wearable("Unknown $deviceSource $productionSource", R.drawable.unknown)
        }
    }
}