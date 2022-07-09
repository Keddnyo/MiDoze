package io.github.keddnyo.midoze.utils.devices

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): String {
        return when (deviceSource.toString()) {
            "12" -> "Amazfit Bip"

            "20" -> "Amazfit Bip2"
            "24" -> "Mi Band 4 NFC"
            "25" -> "Mi Band 4 Global"
            "28" -> "Amazfit Bip S"
            "29" -> "Amazfit Bip S Lite"

            "30" -> "Amazfit Verge Lite Global"
            "31" -> "Mi Band 3i"
            "35" -> "Amazfit GTR 47mm Chinese"
            "36" -> "Amazfit GTR 47mm Global"
            "37" -> "Amazfit GTR 42mm Chinese"
            "38" -> "Amazfit GTR 42mm Global"
            "39" -> "Amazfit Bip Lite Chinese"

            "40" -> "Amazfit GTS Chinese"
            "41" -> "Amazfit GTS Global"
            "42" -> "Amazfit Bip Lite Global"
            "44" -> "Amazfit GTR 42mm Lite Global"
            "46" -> "Amazfit GTR 47mm Lite Global"

            "50" -> "Amazfit T-Rex"
            "51" -> "Amazfit GTR 42mm SWK"
            "52" -> "Amazfit GTR 42mm SWK Global"
            "53" -> "Amazfit X Chinese"
            "56" -> "Zepp Z Chinese"
            "57" -> "Zepp E Circle Chinese"
            "58" -> "Mi Band 5 NFC"
            "59" -> "Mi Band 5"

            "61" -> "Zepp E Square Chinese"
            "62" -> "Amazfit Neo"
            "63" -> "Amazfit GTR2 Chinese"
            "64" -> "Amazfit GTR2 Global"
            "67" -> "Amazfit Pop Pro"
            "68" -> "Amazfit Pop"
            "69" -> "Amazfit Bip U Pro"

            "70" -> "Amazfit Bip U"
            "71" -> "Amazfit X Global"
            "73" -> "Amazfit Band 5"
            "76" -> "Zepp Z Global"
            "77" -> "Amazfit GTS2 Chinese"
            "78" -> "Amazfit GTS2 Global"

            "81" -> "Zepp E Circle Global"
            "82" -> "Zepp E Square Global"
            "83" -> "Amazfit T-Rex Pro Chinese"

            "92" -> "Amazfit GTS2 mini"
            "98" -> "Amazfit GTR2 eSIM" // Found

            "104" -> "Amazfit GTR 3 Pro" // Found

            "113" -> "Amazfit Bip"

            "200" -> "Amazfit T-Rex Pro Global"
            "206" -> "Amazfit GTR2e Chinese"
            "207" -> "Amazfit GTS2e Chinese"
            "209" -> "Amazfit GTR2e Global"

            "210" -> "Amazfit GTS2e Global"
            "211" -> "Mi Band 6 Chinese NFC"
            "212" -> "Mi Band 6 Global"

            "224" -> "Amazfit GTS3 Chinese"
            "225" -> "Amazfit GTS3 Global"
            "226" -> "Amazfit GTR3 Chinese"
            "227" -> "Amazfit GTR3 Global"
            "229" -> "Amazfit GTR3 Pro Chinese"

            "230" -> "Amazfit GTR3 Pro Global"

            // "243" -> "Amazfit GTR 47mm Titanium" // Found

            "262" -> "Xiaomi Smart Band 7" // 257
            "263" -> "Xiaomi Smart Band 7" // 258
            "264" -> "Xiaomi Smart Band 7" // 259
            "265" -> "Xiaomi Smart Band 7" // 260

            else -> "Unknown $deviceSource $productionSource"
        }
    }
}