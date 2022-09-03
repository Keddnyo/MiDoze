package io.github.keddnyo.midoze.local.dataModels

class Watchface {
    data class WatchfaceDevice(
        val deviceName: String,
        val deviceAlias: String,
        val hasCategories: Boolean
    )

    data class WatchfaceData(
        val title: String,
        val categoryName: String,
        val deviceName: String,
        val deviceAlias: String,
        val introduction: String,
        val url: String
    )

    data class WatchfaceDataArray(
        val title: String,
        val stack: ArrayList<WatchfaceData>,
        val hasCategories: Boolean
    )

    data class WatchfaceDataArrayGlobal(
        val title: String,
        val alias: String,
        val stack: ArrayList<WatchfaceDataArray>
    )
}