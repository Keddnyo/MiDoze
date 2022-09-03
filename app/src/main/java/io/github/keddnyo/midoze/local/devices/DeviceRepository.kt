package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Firmware

class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int): Firmware.Device {
        return when (deviceSource.toString()) {
            "12" -> Firmware.Device("Amazfit Bip", R.drawable.amazfit_bip)

            "20" -> Firmware.Device("Amazfit Bip S CN", R.drawable.amazfit_bip_s)
            "24" -> Firmware.Device("Xiaomi Mi Band 4 NFC", R.drawable.mi_band_4_nfc)
            "25" -> Firmware.Device("Xiaomi Mi Band 4 GL", R.drawable.mi_band_4)
            "28" -> Firmware.Device("Amazfit Bip S GL", R.drawable.amazfit_bip_s)
            "29" -> Firmware.Device("Amazfit Bip S Lite GL", R.drawable.amazfit_bip_s)

            "30" -> Firmware.Device("Amazfit Verge Lite GL", R.drawable.amazfit_verge_lite)
            "31" -> Firmware.Device("Xiaomi Mi Band 3i", R.drawable.mi_band_3i)
            "35" -> Firmware.Device("Amazfit GTR 47 CN", R.drawable.amazfit_gtr)
            "36" -> Firmware.Device("Amazfit GTR 47 GL", R.drawable.amazfit_gtr)
            "37" -> Firmware.Device("Amazfit GTR 42 CN", R.drawable.amazfit_gtr_42)
            "38" -> Firmware.Device("Amazfit GTR 42 GL", R.drawable.amazfit_gtr_42)
            "39" -> Firmware.Device("Amazfit Bip Lite CN", R.drawable.amazfit_bip)

            "40" -> Firmware.Device("Amazfit GTS CN", R.drawable.amazfit_gts)
            "41" -> Firmware.Device("Amazfit GTS GL", R.drawable.amazfit_gts)
            "42" -> Firmware.Device("Amazfit Bip Lite GL", R.drawable.amazfit_bip)
            "44" -> Firmware.Device("Amazfit GTR 42 Lite GL", R.drawable.amazfit_gtr_42)
            "46" -> Firmware.Device("Amazfit GTR 47 Lite GL", R.drawable.amazfit_gtr)

            "50" -> Firmware.Device("Amazfit T-Rex", R.drawable.amazfit_t_rex)
            "51" -> Firmware.Device("Amazfit GTR 42 SWK", R.drawable.amazfit_gtr_42)
            "52" -> Firmware.Device("Amazfit GTR 42 SWK GL", R.drawable.amazfit_gtr_42)
            "53" -> Firmware.Device("Amazfit X CN", R.drawable.amazfit_x)
            "54" -> Firmware.Device("Amazfit GTR 47 Disney", R.drawable.amazfit_gtr)
            "56" -> Firmware.Device("Zepp Z CN", R.drawable.zepp_z)
            "57" -> Firmware.Device("Zepp E Circle CN", R.drawable.zepp_e_circle)
            "58" -> Firmware.Device("Xiaomi Mi Band 5 NFC", R.drawable.mi_band_5_nfc)
            "59" -> Firmware.Device("Xiaomi Mi Band 5", R.drawable.mi_band_5_nfc)

            "61" -> Firmware.Device("Zepp E Square CN", R.drawable.zepp_e_square)
            "62" -> Firmware.Device("Amazfit Neo", R.drawable.amazfit_neo)
            "63" -> Firmware.Device("Amazfit GTR 2 CN", R.drawable.amazfit_gtr)
            "64" -> Firmware.Device("Amazfit GTR 2 GL", R.drawable.amazfit_gtr)
            "65" -> Firmware.Device("Amazfit Ares", R.drawable.amazfit_ares)
            "67" -> Firmware.Device("Amazfit Pop Pro", R.drawable.amazfit_bip_u)
            "68" -> Firmware.Device("Amazfit Pop", R.drawable.amazfit_bip_u)
            "69" -> Firmware.Device("Amazfit Bip U Pro", R.drawable.amazfit_bip_u)

            "70" -> Firmware.Device("Amazfit Bip U", R.drawable.amazfit_bip_u)
            "71" -> Firmware.Device("Amazfit X GL", R.drawable.amazfit_x)
            "73" -> Firmware.Device("Amazfit Band 5", R.drawable.amazfit_band_5)
            "76" -> Firmware.Device("Zepp Z GL", R.drawable.zepp_z)
            "77" -> Firmware.Device("Amazfit GTS 2 CN", R.drawable.amazfit_gts_2)
            "78" -> Firmware.Device("Amazfit GTS 2 GL", R.drawable.amazfit_gts_2)

            "81" -> Firmware.Device("Zepp E Circle GL", R.drawable.zepp_e_circle)
            "82" -> Firmware.Device("Zepp E Square GL", R.drawable.zepp_e_square)
            "83" -> Firmware.Device("Amazfit T-Rex Pro CN", R.drawable.amazfit_t_rex_pro)

            "91" -> Firmware.Device("Amazfit GTS 2 Mini CN", R.drawable.amazfit_gts_2_mini)
            "92" -> Firmware.Device("Amazfit GTS 2 Mini GL", R.drawable.amazfit_gts_2_mini)
            "98" -> Firmware.Device("Amazfit GTR 2 eSIM", R.drawable.amazfit_gtr)

            "104" -> Firmware.Device("Amazfit Smart Scale", R.drawable.amazfit_smart_scale)

            "200" -> Firmware.Device("Amazfit T-Rex Pro GL", R.drawable.amazfit_t_rex_pro)
            "206" -> Firmware.Device("Amazfit GTR 2e CN", R.drawable.amazfit_gtr_2e)
            "207" -> Firmware.Device("Amazfit GTS 2e CN", R.drawable.amazfit_gts_2)
            "209" -> Firmware.Device("Amazfit GTR 2e GL", R.drawable.amazfit_gtr_2e)

            "210" -> Firmware.Device("Amazfit GTS 2e GL", R.drawable.amazfit_gts_2)
            "211" -> Firmware.Device("Xiaomi Mi Band 6 NFC", R.drawable.mi_band_6)
            "212" -> Firmware.Device("Xiaomi Mi Band 6 GL", R.drawable.mi_band_6)

            "224" -> Firmware.Device("Amazfit GTS 3 CN", R.drawable.amazfit_gts_3)
            "225" -> Firmware.Device("Amazfit GTS 3 GL", R.drawable.amazfit_gts_3)
            "226" -> Firmware.Device("Amazfit GTR 3 CN", R.drawable.amazfit_gtr_3)
            "227" -> Firmware.Device("Amazfit GTR 3 GL", R.drawable.amazfit_gtr_3)
            "229" -> Firmware.Device("Amazfit GTR 3 Pro CN", R.drawable.amazfit_gtr_3)

            "230" -> Firmware.Device("Amazfit GTR 3 Pro GL", R.drawable.amazfit_gtr_3)

            "242" -> Firmware.Device("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "243" -> Firmware.Device("Amazfit GTS 2 Mini 2022", R.drawable.amazfit_gts_2_mini)
            "244" -> Firmware.Device("Amazfit GTR 2", R.drawable.amazfit_gtr_2)
            "245" -> Firmware.Device("Amazfit GTS 2", R.drawable.amazfit_gts_2)
            "246" -> Firmware.Device("Amazfit GTS 4 Mini CN", R.drawable.amazfit_gts_4_mini)
            "247" -> Firmware.Device("Amazfit GTS 4 Mini GL", R.drawable.amazfit_gts_4_mini)

            "254" -> Firmware.Device("Amazfit Band 7", R.drawable.amazfit_band_7)
            "256" -> Firmware.Device("Amazfit Bip 3 Pro", R.drawable.amazfit_bip_3)
            "257" -> Firmware.Device("Amazfit Bip 3", R.drawable.amazfit_bip_3)

            "260" -> Firmware.Device("Xiaomi Smart Band 7 CN NFC", R.drawable.mi_band_7) // +
            // "261" -> Firmware.WatchfaceDataStack("Xiaomi Smart Band 7", R.drawable.mi_band_7)
            "262" -> Firmware.Device("Xiaomi Smart Band 7 CN", R.drawable.mi_band_7) // +
            "263" -> Firmware.Device("Xiaomi Smart Band 7", R.drawable.mi_band_7) // +
            "264" -> Firmware.Device("Xiaomi Smart Band 7", R.drawable.mi_band_7) // +
            "265" -> Firmware.Device("Xiaomi Smart Band 7", R.drawable.mi_band_7) // +
            // "266" -> Firmware.WatchfaceDataStack("Xiaomi Smart Band 7 IN", R.drawable.mi_band_7)

            "418" -> Firmware.Device("Amazfit T-Rex 2 CN", R.drawable.amazfit_t_rex_2)
            "419" -> Firmware.Device("Amazfit T-Rex 2 GL", R.drawable.amazfit_t_rex_2)

            else -> Firmware.Device("Unknown $deviceSource", R.drawable.unknown)
        }
    }
}