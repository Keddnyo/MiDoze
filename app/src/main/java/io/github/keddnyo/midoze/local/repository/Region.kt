package io.github.keddnyo.midoze.local.repository

open class Region (
    val country: String,
    val language: String,
) {
    object Chinese : Region(
        country = "CH",
        language = "zh_CH",
    )

    object UnitedStates : Region(
        country = "US",
        language = "en_US",
    )
}