package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Device

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): Device {
        return when (deviceSource.toString()) {
            "12" -> Device("Amazfit Bip", R.drawable.amazfit_bip)

            "20" -> Device("Amazfit Bip S", R.drawable.amazfit_bip) // TODO: May be replace
            "24" -> Device("Xiaomi Mi Band 4 NFC", R.drawable.mi_band_4_nfc)
            "25" -> Device("Xiaomi Mi Band 4 GL", R.drawable.mi_band_4)
            "28" -> Device("Amazfit Bip S", R.drawable.amazfit_bip)
            "29" -> Device("Amazfit Bip S Lite", R.drawable.amazfit_bip_s_lite)

            "30" -> Device("Amazfit Verge Lite GL", R.drawable.amazfit_verge_lite)
            "31" -> Device("Xiaomi Mi Band 3i", R.drawable.mi_band_3i)
            "35" -> Device("Amazfit GTR 47 CH", R.drawable.amazfit_gtr)
            "36" -> Device("Amazfit GTR 47 GL", R.drawable.amazfit_gtr)
            "37" -> Device("Amazfit GTR 42 CH", R.drawable.amazfit_gtr_42)
            "38" -> Device("Amazfit GTR 42 GL", R.drawable.amazfit_gtr_42)
            "39" -> Device("Amazfit Bip Lite CH", R.drawable.amazfit_bip)

            "40" -> Device("Amazfit GTS CH", R.drawable.amazfit_gts)
            "41" -> Device("Amazfit GTS GL", R.drawable.amazfit_gts)
            "42" -> Device("Amazfit Bip Lite GL", R.drawable.amazfit_bip)
            "44" -> Device("Amazfit GTR 42 Lite GL", R.drawable.amazfit_gtr_42) // Not Fount // TODO: May be replace
            "46" -> Device("Amazfit GTR 47 Lite GL", R.drawable.amazfit_gtr) // TODO: May be replace

            "50" -> Device("Amazfit T-Rex", R.drawable.amazfit_t_rex)
            "51" -> Device("Amazfit GTR 42 SWK", R.drawable.amazfit_gtr_42) // TODO: May be replace
            "52" -> Device("Amazfit GTR 42 SWK GL", R.drawable.amazfit_gtr_42) // TODO: May be replace
            "53" -> Device("Amazfit X CH", R.drawable.amazfit_x)
            "54" -> Device("Amazfit GTR 47 Disney", R.drawable.amazfit_gtr) // TODO: May be replace
            "56" -> Device("Zepp Z CH", R.drawable.zepp_z)
            "57" -> Device("Zepp E Circle CH", R.drawable.zepp_e_circle)
            "58" -> Device("Xiaomi Mi Band 5 NFC", R.drawable.mi_band_5_nfc)
            "59" -> Device("Xiaomi Mi Band 5", R.drawable.mi_band_5_nfc)

            "61" -> Device("Zepp E Square CH", R.drawable.zepp_e_square)
            "62" -> Device("Amazfit Neo", R.drawable.amazfit_neo)
            "63" -> Device("Amazfit GTR 2 CH", R.drawable.amazfit_gtr)
            "64" -> Device("Amazfit GTR 2 GL", R.drawable.amazfit_gtr)
            "65" -> Device("Amazfit Ares", R.drawable.amazfit_ares)
            "67" -> Device("Amazfit Pop Pro", R.drawable.amazfit_bip_u)
            "68" -> Device("Amazfit Pop", R.drawable.amazfit_bip_u)
            "69" -> Device("Amazfit Bip U Pro", R.drawable.amazfit_bip_u)

            "70" -> Device("Amazfit Bip U", R.drawable.amazfit_bip_u)
            "71" -> Device("Amazfit X GL", R.drawable.amazfit_x)
            "73" -> Device("Amazfit Band 5", R.drawable.amazfit_band_5)
            "76" -> Device("Zepp Z GL", R.drawable.zepp_z)
            "77" -> Device("Amazfit GTS 2 CH", R.drawable.amazfit_gts_2)
            "78" -> Device("Amazfit GTS 2 GL", R.drawable.amazfit_gts_2)

            "81" -> Device("Zepp E Circle GL", R.drawable.zepp_e_circle)
            "82" -> Device("Zepp E Square GL", R.drawable.zepp_e_square)
            "83" -> Device("Amazfit T-Rex Pro CH", R.drawable.amazfit_t_rex) // TODO: May be replace

            "92" -> Device("Amazfit GTS 2 Mini", R.drawable.amazfit_gts_2_mini)
            "98" -> Device("Amazfit GTR 2 eSIM", R.drawable.amazfit_gtr)

            "104" -> Device("Amazfit GTS 2 Mini", R.drawable.amazfit_gts_2_mini)

            // "113" -> DeviceData("Amazfit Bip", R.drawable.amazfit_bip)

            "200" -> Device("Amazfit T-Rex Pro GL", R.drawable.amazfit_t_rex) // TODO: May be replace
            "206" -> Device("Amazfit GTR 2e CH", R.drawable.amazfit_gtr_2e)
            "207" -> Device("Amazfit GTS 2e CH", R.drawable.amazfit_gts_2)
            "209" -> Device("Amazfit GTR 2e GL", R.drawable.amazfit_gtr_2e)

            "210" -> Device("Amazfit GTS 2e GL", R.drawable.amazfit_gts_2)
            "211" -> Device("Xiaomi Mi Band 6 NFC", R.drawable.mi_band_6)
            "212" -> Device("Xiaomi Mi Band 6 GL", R.drawable.mi_band_6)

            "224" -> Device("Amazfit GTS 3 CH", R.drawable.amazfit_gts_3)
            "225" -> Device("Amazfit GTS 3 GL", R.drawable.amazfit_gts_3)
            "226" -> Device("Amazfit GTR 3 CH", R.drawable.amazfit_gtr_3)
            "227" -> Device("Amazfit GTR 3 GL", R.drawable.amazfit_gtr_3)
            "229" -> Device("Amazfit GTR 3 Pro CH", R.drawable.amazfit_gtr_3)

            "230" -> Device("Amazfit GTR 3 Pro GL", R.drawable.amazfit_gtr_3)

            "242" -> Device("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "243" -> Device("Amazfit GTS 2 Mini 2022", R.drawable.amazfit_gts_2_mini)
            "244" -> Device("Amazfit GTR 2", R.drawable.amazfit_gtr_2)
            "245" -> Device("Amazfit GTS 2", R.drawable.amazfit_gts_2)

            "254" -> Device("Amazfit Band 7", R.drawable.amazfit_band_5)
            "256" -> Device("Amazfit Bip 3 Pro", R.drawable.amazfit_bip_3)
            "257" -> Device("Amazfit Bip 3", R.drawable.amazfit_bip_3)

            "262" -> Device("Xiaomi Smart Band 7", R.drawable.mi_band_7)
            "263" -> Device("Xiaomi Smart Band 7", R.drawable.mi_band_7)
            "264" -> Device("Xiaomi Smart Band 7", R.drawable.mi_band_7)
            "265" -> Device("Xiaomi Smart Band 7", R.drawable.mi_band_7)

            else -> Device("Unknown $deviceSource $productionSource", R.drawable.unknown)
        }
    }
}