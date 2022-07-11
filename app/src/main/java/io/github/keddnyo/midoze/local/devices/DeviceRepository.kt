package io.github.keddnyo.midoze.local.devices

@Suppress("UNUSED_EXPRESSION")
class DeviceRepository {
    fun getDeviceNameByCode(deviceSource: Int, productionSource: Int): String {
        return when (deviceSource.toString()) {
            "12" -> "Amazfit Bip"

            "20" -> "Amazfit Bip 2"
            "24" -> "Xiaomi Mi Smart Band 4 NFC"
            "25" -> "Xiaomi Mi Smart Band 4 Global"
            "28" -> "Amazfit Bip S"
            "29" -> "Amazfit Bip S Lite"

            "30" -> "Amazfit Verge Lite Global"
            "31" -> "Xiaomi Mi Band 3i"
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
            "58" -> "Xiaomi Mi Band Smart 5 NFC"
            "59" -> "Xiaomi Mi Smart Band 5"

            "61" -> "Zepp E Square Chinese"
            "62" -> "Amazfit Neo"
            "63" -> "Amazfit GTR 2 Chinese"
            "64" -> "Amazfit GTR 2 Global"
            "67" -> "Amazfit Pop Pro"
            "68" -> "Amazfit Pop"
            "69" -> "Amazfit Bip U Pro"

            "70" -> "Amazfit Bip U"
            "71" -> "Amazfit X Global"
            "73" -> "Amazfit Band 5"
            "76" -> "Zepp Z Global"
            "77" -> "Amazfit GTS 2 Chinese"
            "78" -> "Amazfit GTS 2 Global"

            "81" -> "Zepp E Circle Global"
            "82" -> "Zepp E Square Global"
            "83" -> "Amazfit T-Rex Pro Chinese"

            "92" -> "Amazfit GTS 2 mini"
            "98" -> "Amazfit GTR 2 eSIM"

            "104" -> "Amazfit GTS 2 Mini"

            "113" -> "Amazfit Bip"

            "200" -> "Amazfit T-Rex Pro Global"
            "206" -> "Amazfit GTR 2e Chinese"
            "207" -> "Amazfit GTS 2e Chinese"
            "209" -> "Amazfit GTR 2e Global"

            "210" -> "Amazfit GTS 2e Global"
            "211" -> "Xiaomi Mi Band 6 Chinese NFC"
            "212" -> "Xiaomi Mi Smart Band 6 Global"

            "224" -> "Amazfit GTS 3 Chinese"
            "225" -> "Amazfit GTS 3 Global"
            "226" -> "Amazfit GTR 3 Chinese"
            "227" -> "Amazfit GTR 3 Global"
            "229" -> "Amazfit GTR 3 Pro Chinese"

            "230" -> "Amazfit GTR 3 Pro Global"

            "243" -> "Amazfit GTR 3 Pro Limited"
            "244" -> "Amazfit GTR 2"
            "245" -> "Amazfit GTS 2"

            "256" -> "Amazfit Bip 3 Pro"
            "257" -> "Amazfit Bip 3"

            // "243" -> "Amazfit GTR 47mm Titanium" // Found

            "262" -> "Xiaomi Smart Band 7" // 257
            "263" -> "Xiaomi Smart Band 7" // 258
            "264" -> "Xiaomi Smart Band 7" // 259
            "265" -> "Xiaomi Smart Band 7" // 260

            else -> "Unknown $deviceSource $productionSource"
        }
    }
}