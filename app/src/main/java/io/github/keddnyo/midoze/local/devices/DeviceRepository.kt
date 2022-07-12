package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.R

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): DeviceData {
        return when (deviceSource.toString()) {
            "12" -> DeviceData(R.drawable.amazfit_bip, "Amazfit Bip")

            "20" -> "Amazfit Bip 2"
            "24" -> DeviceData(R.drawable.mi_band_4_nfc, "Xiaomi Mi Band 4 NFC")
            "25" -> "Xiaomi Mi Band 4 GL"
            "28" -> "Amazfit Bip S"
            "29" -> "Amazfit Bip S Lite"

            "30" -> "Amazfit Verge Lite GL"
            "31" -> "Xiaomi Mi Band 3i"
            "35" -> "Amazfit GTR 47mm CH"
            "36" -> "Amazfit GTR 47mm GL"
            "37" -> "Amazfit GTR 42mm CH"
            "38" -> "Amazfit GTR 42mm GL"
            "39" -> "Amazfit Bip Lite CH"

            "40" -> "Amazfit GTS CH"
            "41" -> "Amazfit GTS GL"
            "42" -> "Amazfit Bip Lite GL"
            "44" -> "Amazfit GTR 42mm Lite GL"
            "46" -> "Amazfit GTR 47mm Lite GL"

            "50" -> "Amazfit T-Rex"
            "51" -> "Amazfit GTR 42mm SWK"
            "52" -> "Amazfit GTR 42mm SWK GL"
            "53" -> "Amazfit X CH"
            "54" -> "Amazfit GTR 47mm Disney"
            "56" -> "Zepp Z CH"
            "57" -> "Zepp E Circle CH"
            "58" -> DeviceData(R.drawable.mi_band_5_nfc, "Xiaomi Mi Band 5 NFC")
            "59" -> "Xiaomi Mi Band 5"

            "61" -> "Zepp E Square CH"
            "62" -> "Amazfit Neo"
            "63" -> "Amazfit GTR 2 CH"
            "64" -> "Amazfit GTR 2 GL"
            "67" -> "Amazfit Pop Pro"
            "68" -> "Amazfit Pop"
            "69" -> "Amazfit Bip U Pro"

            "70" -> "Amazfit Bip U"
            "71" -> "Amazfit X GL"
            "73" -> DeviceData(R.drawable.amazfit_band_5, "Amazfit Band 5")
            "76" -> "Zepp Z GL"
            "77" -> "Amazfit GTS 2 CH"
            "78" -> "Amazfit GTS 2 GL"

            "81" -> "Zepp E Circle GL"
            "82" -> "Zepp E Square GL"
            "83" -> "Amazfit T-Rex Pro CH"

            "92" -> "Amazfit GTS 2 mini"
            "98" -> "Amazfit GTR 2 eSIM"

            "104" -> "Amazfit GTS 2 Mini"

            "113" -> DeviceData(R.drawable.amazfit_bip, "Amazfit Bip")

            "200" -> "Amazfit T-Rex Pro GL"
            "206" -> "Amazfit GTR 2e CH"
            "207" -> "Amazfit GTS 2e CH"
            "209" -> "Amazfit GTR 2e GL"

            "210" -> "Amazfit GTS 2e GL"
            "211" -> DeviceData(R.drawable.mi_band_6, "Xiaomi Mi Band 6 NFC")
            "212" -> DeviceData(R.drawable.mi_band_6, "Xiaomi Mi Band 6 GL")

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

            "262" -> DeviceData(R.drawable.mi_band_7, "Xiaomi Smart Band 7") // 257
            "263" -> DeviceData(R.drawable.mi_band_7, "Xiaomi Smart Band 7") // 258
            "264" -> DeviceData(R.drawable.mi_band_7, "Xiaomi Smart Band 7") // 259
            "265" -> DeviceData(R.drawable.mi_band_7, "Xiaomi Smart Band 7") // 260

            else -> "Unknown $deviceSource $productionSource"
        }
    }
}