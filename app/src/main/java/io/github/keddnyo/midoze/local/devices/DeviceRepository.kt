package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.FirmwareData

class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int): FirmwareData.Device {
        return when (deviceSource.toString()) {
            "12" -> FirmwareData.Device("Amazfit Bip", R.drawable.amazfit_bip)

            "20" -> FirmwareData.Device("Amazfit Bip S CN", R.drawable.amazfit_bip_s)
            "24" -> FirmwareData.Device("Xiaomi Mi Band 4 NFC", R.drawable.mi_band_4_nfc)
            "25" -> FirmwareData.Device("Xiaomi Mi Band 4 GL", R.drawable.mi_band_4)
            "28" -> FirmwareData.Device("Amazfit Bip S GL", R.drawable.amazfit_bip_s)
            "29" -> FirmwareData.Device("Amazfit Bip S Lite GL", R.drawable.amazfit_bip_s)

            "30" -> FirmwareData.Device("Amazfit Verge Lite GL", R.drawable.amazfit_verge_lite)
            "31" -> FirmwareData.Device("Xiaomi Mi Band 3i", R.drawable.mi_band_3i)
            "35" -> FirmwareData.Device("Amazfit GTR 47 CN", R.drawable.amazfit_gtr)
            "36" -> FirmwareData.Device("Amazfit GTR 47 GL", R.drawable.amazfit_gtr)
            "37" -> FirmwareData.Device("Amazfit GTR 42 CN", R.drawable.amazfit_gtr_42)
            "38" -> FirmwareData.Device("Amazfit GTR 42 GL", R.drawable.amazfit_gtr_42)
            "39" -> FirmwareData.Device("Amazfit Bip Lite CN", R.drawable.amazfit_bip)

            "40" -> FirmwareData.Device("Amazfit GTS CN", R.drawable.amazfit_gts)
            "41" -> FirmwareData.Device("Amazfit GTS GL", R.drawable.amazfit_gts)
            "42" -> FirmwareData.Device("Amazfit Bip Lite GL", R.drawable.amazfit_bip)
            "44" -> FirmwareData.Device("Amazfit GTR 42 Lite GL", R.drawable.amazfit_gtr_42)
            "46" -> FirmwareData.Device("Amazfit GTR 47 Lite GL", R.drawable.amazfit_gtr)

            "50" -> FirmwareData.Device("Amazfit T-Rex", R.drawable.amazfit_t_rex)
            "51" -> FirmwareData.Device("Amazfit GTR 42 SWK", R.drawable.amazfit_gtr_42)
            "52" -> FirmwareData.Device("Amazfit GTR 42 SWK GL", R.drawable.amazfit_gtr_42)
            "53" -> FirmwareData.Device("Amazfit X CN", R.drawable.amazfit_x)
            "54" -> FirmwareData.Device("Amazfit GTR 47 Disney", R.drawable.amazfit_gtr)
            "56" -> FirmwareData.Device("Zepp Z CN", R.drawable.zepp_z)
            "57" -> FirmwareData.Device("Zepp E Circle CN", R.drawable.zepp_e_circle)
            "58" -> FirmwareData.Device("Xiaomi Mi Band 5 NFC", R.drawable.mi_band_5_nfc)
            "59" -> FirmwareData.Device("Xiaomi Mi Band 5", R.drawable.mi_band_5_nfc)

            "61" -> FirmwareData.Device("Zepp E Square CN", R.drawable.zepp_e_square)
            "62" -> FirmwareData.Device("Amazfit Neo", R.drawable.amazfit_neo)
            "63" -> FirmwareData.Device("Amazfit GTR 2 CN", R.drawable.amazfit_gtr)
            "64" -> FirmwareData.Device("Amazfit GTR 2 GL", R.drawable.amazfit_gtr)
            "65" -> FirmwareData.Device("Amazfit Ares", R.drawable.amazfit_ares)
            "67" -> FirmwareData.Device("Amazfit Pop Pro", R.drawable.amazfit_bip_u)
            "68" -> FirmwareData.Device("Amazfit Pop", R.drawable.amazfit_bip_u)
            "69" -> FirmwareData.Device("Amazfit Bip U Pro", R.drawable.amazfit_bip_u)

            "70" -> FirmwareData.Device("Amazfit Bip U", R.drawable.amazfit_bip_u)
            "71" -> FirmwareData.Device("Amazfit X GL", R.drawable.amazfit_x)
            "73" -> FirmwareData.Device("Amazfit Band 5", R.drawable.amazfit_band_5)
            "76" -> FirmwareData.Device("Zepp Z GL", R.drawable.zepp_z)
            "77" -> FirmwareData.Device("Amazfit GTS 2 CN", R.drawable.amazfit_gts_2)
            "78" -> FirmwareData.Device("Amazfit GTS 2 GL", R.drawable.amazfit_gts_2)

            "81" -> FirmwareData.Device("Zepp E Circle GL", R.drawable.zepp_e_circle)
            "82" -> FirmwareData.Device("Zepp E Square GL", R.drawable.zepp_e_square)
            "83" -> FirmwareData.Device("Amazfit T-Rex Pro CN", R.drawable.amazfit_t_rex_pro)

            "91" -> FirmwareData.Device("Amazfit GTS 2 Mini CN", R.drawable.amazfit_gts_2_mini)
            "92" -> FirmwareData.Device("Amazfit GTS 2 Mini GL", R.drawable.amazfit_gts_2_mini)
            "98" -> FirmwareData.Device("Amazfit GTR 2 eSIM", R.drawable.amazfit_gtr)

            "104" -> FirmwareData.Device("Amazfit Smart Scale", R.drawable.amazfit_smart_scale)

            "200" -> FirmwareData.Device("Amazfit T-Rex Pro GL", R.drawable.amazfit_t_rex_pro)
            "206" -> FirmwareData.Device("Amazfit GTR 2e CN", R.drawable.amazfit_gtr_2e)
            "207" -> FirmwareData.Device("Amazfit GTS 2e CN", R.drawable.amazfit_gts_2)
            "209" -> FirmwareData.Device("Amazfit GTR 2e GL", R.drawable.amazfit_gtr_2e)

            "210" -> FirmwareData.Device("Amazfit GTS 2e GL", R.drawable.amazfit_gts_2)
            "211" -> FirmwareData.Device("Xiaomi Mi Band 6 NFC", R.drawable.mi_band_6)
            "212" -> FirmwareData.Device("Xiaomi Mi Band 6 GL", R.drawable.mi_band_6)

            "224" -> FirmwareData.Device("Amazfit GTS 3 CN", R.drawable.amazfit_gts_3)
            "225" -> FirmwareData.Device("Amazfit GTS 3 GL", R.drawable.amazfit_gts_3)
            "226" -> FirmwareData.Device("Amazfit GTR 3 CN", R.drawable.amazfit_gtr_3)
            "227" -> FirmwareData.Device("Amazfit GTR 3 GL", R.drawable.amazfit_gtr_3)
            "229" -> FirmwareData.Device("Amazfit GTR 3 Pro CN", R.drawable.amazfit_gtr_3)

            "230" -> FirmwareData.Device("Amazfit GTR 3 Pro GL", R.drawable.amazfit_gtr_3)

            "242" -> FirmwareData.Device("Amazfit GTR 3 Pro Ltd", R.drawable.amazfit_gtr_3)
            "243" -> FirmwareData.Device("Amazfit GTS 2 Mini 2022", R.drawable.amazfit_gts_2_mini)
            "244" -> FirmwareData.Device("Amazfit GTR 2", R.drawable.amazfit_gtr_2)
            "245" -> FirmwareData.Device("Amazfit GTS 2", R.drawable.amazfit_gts_2)
            "246" -> FirmwareData.Device("Amazfit GTS 4 Mini CN", R.drawable.amazfit_gts_4_mini)
            "247" -> FirmwareData.Device("Amazfit GTS 4 Mini GL", R.drawable.amazfit_gts_4_mini)

            "254" -> FirmwareData.Device("Amazfit Band 7", R.drawable.amazfit_band_7)
            "256" -> FirmwareData.Device("Amazfit Bip 3 Pro", R.drawable.amazfit_bip_3)
            "257" -> FirmwareData.Device("Amazfit Bip 3", R.drawable.amazfit_bip_3)

            "260" -> FirmwareData.Device("Xiaomi Smart Band 7 CN NFC", R.drawable.mi_band_7) // +
            // "261" -> Firmware.WatchfaceDataStack("Xiaomi Smart Band 7", R.drawable.mi_band_7)
            "262" -> FirmwareData.Device("Xiaomi Smart Band 7 CN", R.drawable.mi_band_7) // +
            "263" -> FirmwareData.Device("Xiaomi Smart Band 7", R.drawable.mi_band_7) // +
            "264" -> FirmwareData.Device("Xiaomi Smart Band 7", R.drawable.mi_band_7) // +
            "265" -> FirmwareData.Device("Xiaomi Smart Band 7", R.drawable.mi_band_7) // +
            // "266" -> Firmware.WatchfaceDataStack("Xiaomi Smart Band 7 IN", R.drawable.mi_band_7)

            "418" -> FirmwareData.Device("Amazfit T-Rex 2 CN", R.drawable.amazfit_t_rex_2)
            "419" -> FirmwareData.Device("Amazfit T-Rex 2 GL", R.drawable.amazfit_t_rex_2)

            else -> FirmwareData.Device("Unknown $deviceSource", R.drawable.unknown)
        }
    }
}