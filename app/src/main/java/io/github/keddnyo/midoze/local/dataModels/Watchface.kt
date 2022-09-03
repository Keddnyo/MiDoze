package io.github.keddnyo.midoze.local.dataModels

class Watchface {
    data class Device(
        val name: String,
        val alias: String,
        val preview: Int?,
    )

    data class WatchfaceData(
        val alias: String,
        val title: String,
        val categoryName: String,
        val introduction: String,
        val url: String
    )

    data class WatchfaceDataStack(
        val name: String,
        val preview: Int?,
        val watchfaceData: ArrayList<WatchfaceData>
    )
}