package io.github.keddnyo.midoze.local.repository

open class WearableRegion (
    val country: String,
    val language: String,
) {
    object CHINESE : WearableRegion(
        country = "CH",
        language = "zh_CH",
    )

    object STATES : WearableRegion(
        country = "US",
        language = "en_US",
    )
}