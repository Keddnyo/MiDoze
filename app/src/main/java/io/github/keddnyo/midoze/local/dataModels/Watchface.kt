package io.github.keddnyo.midoze.local.dataModels

class WatchfaceData {
    data class Device(
        val name: String,
        val alias: String,
        val preview: Int?,
    )

    data class Watchface(
        val alias: String,
        val title: String,
        val url: String
    )

    data class WatchfaceArray(
        val name: String,
        val watchface: ArrayList<Watchface>
    )
}