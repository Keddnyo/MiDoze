package io.github.keddnyo.midoze.local.dataModels

import org.json.JSONObject

class WatchfaceData {
    data class Device(
        val name: String,
        val alias: String,
        val preview: Int?,
    )

    data class Watchface(
        val alias: String,
        val title: String,
        val url: String,
        val watchfaceData: JSONObject
    )

    data class WatchfaceArray(
        val name: String,
        val watchface: ArrayList<Watchface>
    )
}